package org.catroid.catrobat.newui.db.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.catroid.catrobat.newui.db.util.CatroidDBHelper;
import org.catroid.catrobat.newui.db.util.DataContract;

import java.util.HashMap;
import java.util.Map;

public class CatroidContentProvider extends ContentProvider {
    private enum MatchType {
        COLLECTION_MATCH,
        MEMBER_MATCH
    }

    private class ContentDescriptionMatch {
        private MatchType mMatchType;
        private ContentDescription mContentDescription;

        ContentDescriptionMatch(MatchType matchType, ContentDescription contentDescription) {
            mMatchType = matchType;
            mContentDescription = contentDescription;
        }
    }

    private static final String TAG = "BCP";

    private UriMatcher mUriMatcher;
    private CatroidDBHelper mDbHelper;
    private Map<String, ContentDescription> mContentDescriptions;
    private Map<Integer, ContentDescriptionMatch> mURIMatcherMapping;
    private int mContentDescriptionMappingId = 1;

    @Override
    public boolean onCreate() {
        Context context = getContext();

        registerContentDescriptions();

        mDbHelper = new CatroidDBHelper(context);
        mUriMatcher = buildUriMatcher();

        return true;
    }

    private void registerContentDescriptions() {
        mContentDescriptions = new HashMap<>();

        registerContentDescription(new ProjectContentDescription());
        registerContentDescription(new SceneContentDescription());
        registerContentDescription(new SpriteContentDescription());
        registerContentDescription(new LookContentDescription());
        registerContentDescription(new SoundContentDescription());
    }

    private void registerContentDescription(ContentDescription contentDescription) {
        mContentDescriptions.put(contentDescription.getPath(), contentDescription);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        ContentDescriptionMatch descriptionMatch = getContentDescriptionMatch(uri);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        return db.query(descriptionMatch.mContentDescription.getTableName(), projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        ContentDescriptionMatch descriptionMatch = getContentDescriptionMatch(uri);

        if (descriptionMatch != null) {
            return "application/Catroid." + descriptionMatch.mContentDescription.getPath();
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        ContentDescriptionMatch descriptionMatch = getContentDescriptionMatch(uri);

        ContentDescription description = descriptionMatch.mContentDescription;

        if (descriptionMatch.mMatchType == MatchType.COLLECTION_MATCH) {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            long id = db.insert(description.getTableName(), null, contentValues);
            if (id > 0) {
                Uri itemUri = ContentUris.withAppendedId(description.getItemsUri(), id);

                getContext().getContentResolver().notifyChange(itemUri, null);

                return itemUri;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        ContentDescriptionMatch descriptionMatch = getContentDescriptionMatch(uri);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentDescription description = descriptionMatch.mContentDescription;

        int deletedRecordsCount;
        if (descriptionMatch.mMatchType == MatchType.MEMBER_MATCH) {
            String idString = uri.getLastPathSegment();

            deletedRecordsCount = db.delete(description.getTableName(), description.getIdColumn() + " = ? ", new String[]{idString});
        } else {
            deletedRecordsCount = db.delete(description.getTableName(), selection, selectionArgs);
        }

        if (deletedRecordsCount != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deletedRecordsCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        ContentDescriptionMatch descriptionMatch = getContentDescriptionMatch(uri);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentDescription description = descriptionMatch.mContentDescription;

        int updatedRecordsCount;
        if (descriptionMatch.mMatchType == MatchType.MEMBER_MATCH) {
            String idString = uri.getLastPathSegment();

            updatedRecordsCount = db.update(description.getTableName(), contentValues, description.getIdColumn() + " = ? ", new String[]{idString});
        } else {
            updatedRecordsCount = db.update(description.getTableName(), contentValues, selection, selectionArgs);
        }

        if (updatedRecordsCount != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updatedRecordsCount;
    }


    private UriMatcher buildUriMatcher() {
        mURIMatcherMapping = new HashMap<>();

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        for (ContentDescription description : mContentDescriptions.values()) {
            String itemsPath = description.getPath();
            String itemPath = description.getPath() + "/#";

            int itemsMappingId = generateContentDescriptionMappingId();
            int itemMappingId = generateContentDescriptionMappingId();

            uriMatcher.addURI(DataContract.CONTENT_AUTHORITY, itemsPath, itemsMappingId);
            uriMatcher.addURI(DataContract.CONTENT_AUTHORITY, itemPath, itemMappingId);

            mURIMatcherMapping.put(itemsMappingId, new ContentDescriptionMatch(MatchType.COLLECTION_MATCH, description));
            mURIMatcherMapping.put(itemMappingId, new ContentDescriptionMatch(MatchType.MEMBER_MATCH, description));
        }

        return uriMatcher;
    }

    private ContentDescriptionMatch getContentDescriptionMatch(Uri uri) {
        int matchId = mUriMatcher.match(uri);

        if (matchId > 0) {
            return mURIMatcherMapping.get(matchId);
        } else {
            throw new UnsupportedOperationException("Could not match uri: " + uri.toString());
        }
    }

    public synchronized int generateContentDescriptionMappingId() {
        int newId = mContentDescriptionMappingId++;

        return newId;
    }
}
