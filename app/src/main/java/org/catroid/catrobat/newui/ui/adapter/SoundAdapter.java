package org.catroid.catrobat.newui.ui.adapter;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.SoundInfo;
import org.catroid.catrobat.newui.db.brigde.SceneBridge;
import org.catroid.catrobat.newui.db.brigde.SoundBridge;
import org.catroid.catrobat.newui.db.fetchrequest.ChildCollectionFetchRequest;
import org.catroid.catrobat.newui.db.fetchrequest.FetchRequest;
import org.catroid.catrobat.newui.db.util.DataContract;
import org.catroid.catrobat.newui.ui.recyclerview.adapter.DatabaseRecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.recyclerview.viewholder.ListViewHolder;
import org.catroid.catrobat.newui.ui.recyclerview.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.recyclerview.viewholder.RecyclerViewHolder;

import java.util.ArrayList;

public class SoundAdapter extends DatabaseRecyclerViewAdapter<SoundInfo> {

    private static final String TAG = SoundAdapter.class.getSimpleName();
    private long mSpriteId;

    public SoundAdapter(AppCompatActivity context) {
        super(R.layout.list_item, context);
    }

    public void startLoading(SoundBridge bridge, long spriteId) {
        Log.d(TAG, "Starting to load sounds for sprite #"+ spriteId);
        mSpriteId = spriteId;

        startLoading(bridge);
    }

    @Override
    public RecyclerViewHolder createViewHolder(View view) {
        return new ListViewHolder(view);
    }

    @Override
    public void bindDataToViewHolder(SoundInfo item, RecyclerViewHolder holder, boolean isSelected) {
        ListViewHolder listViewHolder = (ListViewHolder) holder;
        Resources res = listViewHolder.mItemView.getResources();

        String durationDescription = item.getDuration();
        if (durationDescription == null) {
            durationDescription = res.getString(R.string.unknown_duration);
        }

        String durationLabel = res.getString(R.string.sound_duration_label);
        String duration = durationLabel + ": " + durationDescription;

        listViewHolder.mNameView.setText(item.getName());
        listViewHolder.mDetailsView.setText(duration);

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
            return new ChildCollectionFetchRequest(DataContract.SoundEntry.COLUMN_SPRITE_ID, mSpriteId);
        } else {
            return null;
        }
    }
}
