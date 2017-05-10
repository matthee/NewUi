package org.catroid.catrobat.newui.ui.adapter;

import org.catroid.catrobat.newui.data.LookInfo;

import java.util.List;

public class LookAdapter extends RecyclerViewAdapter<LookInfo> {
    public LookAdapter(List<LookInfo> lookInfos, int listItem) {
        super(lookInfos, listItem);
    }

    @Override
    public void bindDataToViewHolder(LookInfo item, ViewHolder holder, boolean isSelected) {
        holder.mNameView.setText(item.getName());
        holder.mDetailsView.setText("");

        if (isSelected) {
            holder.mImageSwitcher.setImageResource(CHECK_MARK_IMAGE_RESOURCE);
        } else {
            holder.mImageSwitcher.setImageDrawable(item.getRoundedDrawable());
        }
    }

}
