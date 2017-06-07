package org.catroid.catrobat.newui.db.contentprovider;

import android.net.Uri;

import org.catroid.catrobat.newui.db.util.DataContract;
import org.catroid.catrobat.newui.db.util.DataContract.LookEntry;

public class LookContentDescription extends ContentDescription {
    @Override
    String getPath() {
        return DataContract.PATH_LOOK;
    }

    @Override
    String getTableName() {
        return LookEntry.TABLE_NAME;
    }

    @Override
    Uri getItemsUri() {
        return LookEntry.LOOKS_URI;
    }

    @Override
    String getIdColumn() {
        return LookEntry._ID;
    }
}
