package org.catroid.catrobat.newui.db.brigde;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.catroid.catrobat.newui.data.SoundInfo;
import org.catroid.catrobat.newui.db.util.DataContract.SoundEntry;

public class SoundBridge extends DatabaseBridge<SoundInfo> {
    public SoundBridge(Context context) {
        super(context);
    }

    @Override
    protected ContentValues serializeForDatabase(SoundInfo item) {
        ContentValues values = new ContentValues();

        values.put(SoundEntry.COLUMN_NAME, item.getName());
        values.put(SoundEntry.COLUMN_FILENAME, item.getFilename());
        values.put(SoundEntry.COLUMN_SPRITE_ID, item.getSpriteId());

        return values;
    }

    @Override
    protected SoundInfo unserializeFromDatabaseCursor(Cursor cursor) {
        SoundInfo soundInfo = new SoundInfo();

        soundInfo.setId(unserializeId(cursor, SoundEntry._ID));
        soundInfo.setName(unserializeString(cursor, SoundEntry.COLUMN_NAME));
        soundInfo.setFilename(unserializeString(cursor, SoundEntry.COLUMN_FILENAME));
        soundInfo.setSpriteId(unserializeLong(cursor, SoundEntry.COLUMN_SPRITE_ID));

        return soundInfo;
    }

    @Override
    protected String[] getProjection() {
        return SoundEntry.getFullProjection();
    }

    @Override
    protected Uri getCollectionUri() {
        return SoundEntry.SOUNDS_URI;
    }

    @Override
    protected Uri getItemUri(long id) {
        return SoundEntry.getSoundUri(id);
    }

    @Override
    protected void beforeDestroy(SoundInfo item) {

    }

    @Override
    protected void afterDestroy(SoundInfo item) {

    }
}
