package org.catroid.catrobat.newui.db.brigde;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.catroid.catrobat.newui.data.LookInfo;
import org.catroid.catrobat.newui.data.Scene;
import org.catroid.catrobat.newui.data.SoundInfo;
import org.catroid.catrobat.newui.data.Sprite;
import org.catroid.catrobat.newui.db.fetchrequest.ChildCollectionFetchRequest;
import org.catroid.catrobat.newui.db.util.DataContract;
import org.catroid.catrobat.newui.db.util.DataContract.SpriteEntry;

import java.util.List;

public class SpriteBridge extends DatabaseBridge<Sprite> {
    public SpriteBridge(Context context) {
        super(context);
    }

    @Override
    protected ContentValues serializeForDatabase(Sprite sprite) {
        ContentValues values = new ContentValues();

        values.put(SpriteEntry.COLUMN_NAME, sprite.getName());
        values.put(SpriteEntry.COLUMN_SCENE_ID, sprite.getSceneId());

        return values;
    }

    @Override
    protected Sprite unserializeFromDatabaseCursor(Cursor cursor) {
        Sprite sprite = new Sprite();

        sprite.setId(cursor.getInt(cursor.getColumnIndex(SpriteEntry._ID)));
        sprite.setName(cursor.getString(cursor.getColumnIndex(SpriteEntry.COLUMN_NAME)));
        sprite.setSceneId(cursor.getLong(cursor.getColumnIndex(SpriteEntry.COLUMN_SCENE_ID)));

        return sprite;
    }

    @Override
    protected String[] getProjection() {
        return SpriteEntry.getFullProjection();
    }

    @Override
    protected Uri getCollectionUri() {
        return SpriteEntry.SPRITE_URI;
    }

    @Override
    protected Uri getItemUri(long id) {
        return SpriteEntry.getSpriteUri(id);
    }

    @Override
    protected void beforeDestroy(Sprite item) {
        destroyLooks(item);
        destroySounds(item);
    }

    @Override
    protected void afterDestroy(Sprite item) {

    }

    private void destroyLooks(Sprite sprite) {
        LookBridge lookBridge = new LookBridge(getContext());

        List<LookInfo> looks = lookBridge.findAll(new ChildCollectionFetchRequest(DataContract.LookEntry.COLUMN_SPRITE_ID, sprite.getId()));

        for (LookInfo look : looks) {
            lookBridge.delete(look);
        }
    }

    private void destroySounds(Sprite sprite) {
        SoundBridge soundBridge = new SoundBridge(getContext());

        List<SoundInfo> sounds = soundBridge.findAll(new ChildCollectionFetchRequest(DataContract.SoundEntry.COLUMN_SPRITE_ID, sprite.getId()));

        for (SoundInfo sound : sounds) {
            soundBridge.delete(sound);
        }
    }
}
