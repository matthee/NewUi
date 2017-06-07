package org.catroid.catrobat.newui.db.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.fingerprint.FingerprintManager;
import android.util.Log;

import org.catroid.catrobat.newui.db.util.DataContract.ProjectEntry;
import org.catroid.catrobat.newui.db.util.DataContract.SceneEntry;
import org.catroid.catrobat.newui.db.util.DataContract.SpriteEntry;
import org.catroid.catrobat.newui.db.util.DataContract.LookEntry;
import org.catroid.catrobat.newui.db.util.DataContract.SoundEntry;


public class CatroidDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "catroid.sqlite3";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DB";

    public CatroidDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createProjectsTableSQL = createProjectsTable();
        Log.d(TAG, "Creating Projects... ");
        Log.d(TAG, createProjectsTableSQL);
        sqLiteDatabase.execSQL(createProjectsTableSQL);

        String createScenesTableSQL = createScenesTable();
        Log.d(TAG, "Creating Scenes... ");
        Log.d(TAG, createScenesTableSQL);
        sqLiteDatabase.execSQL(createScenesTableSQL);


        String createSpritesTableSQL = createSpritesTable();
        Log.d(TAG, "Creating Sprites... ");
        Log.d(TAG, createSpritesTableSQL);
        sqLiteDatabase.execSQL(createSpritesTableSQL);

        String createLooksTableSQL = createLooksTable();
        Log.d(TAG, "Creating Looks... ");
        Log.d(TAG, createLooksTableSQL );
        sqLiteDatabase.execSQL(createLooksTableSQL);

        String createSoundsTableSQL = createSoundsTable();
        Log.d(TAG, "Creating Sounds... ");
        Log.d(TAG, createSoundsTableSQL);
        sqLiteDatabase.execSQL(createSoundsTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versionFrom, int versionTo) {
        Log.d(TAG, "Upgrading ... ");

        sqLiteDatabase.execSQL(SQLHelper.dropTableIfExists(ProjectEntry.TABLE_NAME));
        sqLiteDatabase.execSQL(SQLHelper.dropTableIfExists(SceneEntry.TABLE_NAME));
        sqLiteDatabase.execSQL(SQLHelper.dropTableIfExists(SpriteEntry.TABLE_NAME));
        sqLiteDatabase.execSQL(SQLHelper.dropTableIfExists(LookEntry.TABLE_NAME));
        sqLiteDatabase.execSQL(SQLHelper.dropTableIfExists(SoundEntry.TABLE_NAME));

        onCreate(sqLiteDatabase);
    }

    private String createProjectsTable() {
        return SQLHelper.createTableDefinition(ProjectEntry.TABLE_NAME, new String[]{
                SQLHelper.idColumnDefinition(ProjectEntry._ID),
                SQLHelper.modifierUnique(SQLHelper.stringColumnDefinition(ProjectEntry.COLUMN_NAME)),
                SQLHelper.stringColumnDefinition(ProjectEntry.COLUMN_INFO_TEXT),
                SQLHelper.stringColumnDefinition(ProjectEntry.COLUMN_DESCRIPTION),
                SQLHelper.booleanColumnDefinition(ProjectEntry.COLUMN_FAVORITE),
                SQLHelper.dateColumnDefinition(ProjectEntry.COLUMN_LAST_ACCESS)
        });
    }

    private String createScenesTable() {
        return SQLHelper.createTableDefinition(SceneEntry.TABLE_NAME, new String[]{
                SQLHelper.idColumnDefinition(SceneEntry._ID),
                SQLHelper.integerColumnDefinition(SceneEntry.COLUMN_PROJECT_ID),
                SQLHelper.modifierUnique(SQLHelper.stringColumnDefinition(SceneEntry.COLUMN_NAME))
        });
    }

    private String createSpritesTable() {
        return SQLHelper.createTableDefinition(SpriteEntry.TABLE_NAME, new String[]{
                SQLHelper.idColumnDefinition(SpriteEntry._ID),
                SQLHelper.integerColumnDefinition(SpriteEntry.COLUMN_SCENE_ID),
                SQLHelper.modifierUnique(SQLHelper.stringColumnDefinition(SpriteEntry.COLUMN_NAME))
        });
    }

    private String createLooksTable() {
        return SQLHelper.createTableDefinition(LookEntry.TABLE_NAME, new String[]{
                SQLHelper.idColumnDefinition(LookEntry._ID),
                SQLHelper.stringColumnDefinition(LookEntry.COLUMN_NAME),
                SQLHelper.stringColumnDefinition(LookEntry.COLUMN_FILENAME),
                SQLHelper.integerColumnDefinition(LookEntry.COLUMN_SPRITE_ID)
        });
    }

    private String createSoundsTable() {
        return SQLHelper.createTableDefinition(SoundEntry.TABLE_NAME, new String[]{
                SQLHelper.idColumnDefinition(SoundEntry._ID),
                SQLHelper.stringColumnDefinition(SoundEntry.COLUMN_NAME),
                SQLHelper.stringColumnDefinition(SoundEntry.COLUMN_FILENAME),
                SQLHelper.integerColumnDefinition(SoundEntry.COLUMN_SPRITE_ID)
        });
    }

}
