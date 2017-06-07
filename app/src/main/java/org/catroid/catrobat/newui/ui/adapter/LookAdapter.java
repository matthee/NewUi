package org.catroid.catrobat.newui.ui.adapter;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.LookInfo;
import org.catroid.catrobat.newui.db.brigde.LookBridge;
import org.catroid.catrobat.newui.db.fetchrequest.ChildCollectionFetchRequest;
import org.catroid.catrobat.newui.db.fetchrequest.FetchRequest;
import org.catroid.catrobat.newui.db.util.DataContract;
import org.catroid.catrobat.newui.ui.recyclerview.adapter.DatabaseRecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.recyclerview.viewholder.ListViewHolder;
import org.catroid.catrobat.newui.ui.recyclerview.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.recyclerview.viewholder.RecyclerViewHolder;

import java.util.List;

public class LookAdapter extends DatabaseRecyclerViewAdapter<LookInfo> {

    private static final String TAG = LookAdapter.class.getSimpleName();
    private long mSpriteId;

    public LookAdapter(AppCompatActivity context) {
        super(R.layout.list_item, context);
    }

    public void startLoading(LookBridge bridge, long spriteId) {
        Log.d(TAG, "Starting to load looks for sprite #"+ spriteId);

        mSpriteId = spriteId;

        startLoading(bridge);
    }

    @Override
    public RecyclerViewHolder createViewHolder(final View view) {
        return new ListViewHolder(view);
    }

    @Override
    public void bindDataToViewHolder(LookInfo item, RecyclerViewHolder holder, boolean isSelected) {
        ListViewHolder listViewHolder = (ListViewHolder) holder;
        listViewHolder.mNameView.setText(item.getName());
        listViewHolder.mDetailsView.setText("");

        if (isSelected) {
            listViewHolder.mImageSwitcher.setImageResource(CHECK_MARK_IMAGE_RESOURCE);
        } else {
            listViewHolder.mImageSwitcher.setImageDrawable(item.getRoundedDrawable());
        }

        listViewHolder.updateBackground(isSelected);
    }

    @Override
    protected FetchRequest getFetchRequest() {
        if (mSpriteId != 0) {
            return new ChildCollectionFetchRequest(DataContract.LookEntry.COLUMN_SPRITE_ID, mSpriteId);
        } else {
            return null;
        }
    }
}
