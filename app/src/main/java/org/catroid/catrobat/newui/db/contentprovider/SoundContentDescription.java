package org.catroid.catrobat.newui.db.contentprovider;

import android.net.Uri;

import org.catroid.catrobat.newui.db.util.DataContract;
import org.catroid.catrobat.newui.db.util.DataContract.SoundEntry;

public class SoundContentDescription extends ContentDescription {
    @Override
    String getPath() {
        return DataContract.PATH_SOUND;
    }

    @Override
    String getTableName() {
        return SoundEntry.TABLE_NAME;
    }

    @Override
    Uri getItemsUri() {
        return SoundEntry.SOUNDS_URI;
    }

    @Override
    String getIdColumn() {
        return SoundEntry._ID;
    }
}
