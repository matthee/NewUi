package org.catroid.catrobat.newui.db.brigde;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.catroid.catrobat.newui.data.LookInfo;
import org.catroid.catrobat.newui.db.util.DataContract;
import org.catroid.catrobat.newui.db.util.DataContract.LookEntry;

public class LookBridge extends DatabaseBridge<LookInfo> {
    public LookBridge(Context context) {
        super(context);
    }

    @Override
    protected ContentValues serializeForDatabase(LookInfo look) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(LookEntry.COLUMN_NAME, look.getName());
        contentValues.put(LookEntry.COLUMN_FILENAME, look.getFilename());
        contentValues.put(LookEntry.COLUMN_SPRITE_ID, look.getSpriteId());

        return contentValues;
    }

    @Override
    protected LookInfo unserializeFromDatabaseCursor(Cursor cursor) {
        LookInfo lookInfo = new LookInfo();

        lookInfo.setId(unserializeId(cursor, LookEntry._ID));
        lookInfo.setName(unserializeString(cursor, LookEntry.COLUMN_NAME));
        lookInfo.setFilename(unserializeString(cursor, LookEntry.COLUMN_FILENAME));
        lookInfo.setSpriteId(unserializeLong(cursor, LookEntry.COLUMN_SPRITE_ID));

        return lookInfo;
    }

    @Override
    protected String[] getProjection() {
        return DataContract.LookEntry.getFullProjection();
    }

    @Override
    protected Uri getCollectionUri() {
        return DataContract.LookEntry.LOOKS_URI;
    }

    @Override
    protected Uri getItemUri(long id) {
        return DataContract.LookEntry.getLookUri(id);
    }

    @Override
    protected void beforeDestroy(LookInfo item) {

    }

    @Override
    protected void afterDestroy(LookInfo item) {

    }
}
