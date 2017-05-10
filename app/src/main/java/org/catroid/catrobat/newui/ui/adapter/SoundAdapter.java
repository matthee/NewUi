package org.catroid.catrobat.newui.ui.adapter;

import android.content.res.Resources;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.SoundInfo;

import java.util.ArrayList;

public class SoundAdapter extends RecyclerViewAdapter<SoundInfo> {

    public SoundAdapter(ArrayList<SoundInfo> soundInfos, int listItem) {
        super(soundInfos, listItem);
    }

    @Override
    public void bindDataToViewHolder(SoundInfo item, ViewHolder holder, boolean isSelected) {
        Resources res = holder.mItemView.getResources();

        String durationDescription = item.getDuration();
        if (durationDescription == null) {
            durationDescription = res.getString(R.string.unknown_duration);
        }

        String durationLabel = res.getString(R.string.sound_duration_label);
        String duration = durationLabel + ": " + durationDescription;

        holder.mNameView.setText(item.getName());
        holder.mDetailsView.setText(duration);

        if (isSelected) {
            holder.mImageSwitcher.setImageResource(CHECK_MARK_IMAGE_RESOURCE);
        } else {
            holder.mImageSwitcher.setImageResource(item.getThumbnailResource());
        }
    }
}
