package org.catroid.catrobat.newui.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.catroid.catrobat.newui.R;

import java.util.List;

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements View.OnLongClickListener, RecyclerViewMultiSelectionManagerDelegate<T> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mItemView;
        public TextView mNameView;
        public TextView mDetailsView;
        public ImageSwitcher mImageSwitcher;

        public Animation collapseAnimation;
        public Animation expandAnimation;

        public ViewHolder(final View itemView) {
            super(itemView);

            mItemView = itemView;
            mNameView = (TextView) itemView.findViewById(R.id.name_view);
            mDetailsView = (TextView) itemView.findViewById(R.id.details_view);
            mImageSwitcher = (ImageSwitcher) itemView.findViewById(R.id.slide_trans_imageswitcher);

            collapseAnimation = AnimationUtils.loadAnimation(mItemView.getContext(), R.anim.in_animation);
            expandAnimation = AnimationUtils.loadAnimation(mItemView.getContext(), R.anim.out_animation);
            expandAnimation.setStartOffset(collapseAnimation.getDuration());

            mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    ImageView mImageView = new ImageView(itemView.getContext());

                    mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    mImageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                    return mImageView;
                }
            });
        }


        private void enableAnimations() {
            mImageSwitcher.setOutAnimation(collapseAnimation);
            mImageSwitcher.setInAnimation(expandAnimation);
        }

        private void disableAnimations() {
            mImageSwitcher.setInAnimation(null);
            mImageSwitcher.setOutAnimation(null);
        }


        public void updateBackground(boolean isSelected) {
            if (isSelected) {
                mItemView.setBackgroundColor(SELECTED_ITEM_BACKGROUND_COLOR);
            } else {
                mItemView.setBackgroundColor(0x00000000);
            }
        }

    }
    private List<T> mListItems;
    private T mItemToAnimate;
    private int mItemLayoutId;
    private RecyclerViewMultiSelectionManager<T> mMultiSelectionManager =
            new RecyclerViewMultiSelectionManager<T>();

    private RecyclerViewAdapterDelegate<T> delegate = null;

    private static int SELECTED_ITEM_BACKGROUND_COLOR = 0xFFDDDDDD;
    protected static int CHECK_MARK_IMAGE_RESOURCE = R.drawable.ic_check_circle_black_24dp;

    public RecyclerViewAdapter(List<T> listItems, int itemLayout) {
        mListItems = listItems;
        mItemLayoutId = itemLayout;
        mMultiSelectionManager.setDelegate(this);
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutId, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        T item = mListItems.get(position);

        boolean isSelected = mMultiSelectionManager.getIsSelected(item);

        holder.itemView.setOnLongClickListener(this);

        boolean shouldAnimate = shouldAnimateItem(item);

        if (shouldAnimate) {
            resetItemToAnimate();

            holder.enableAnimations();
        } else {
            holder.disableAnimations();
        }

        bindDataToViewHolder(item, holder, isSelected);

        holder.updateBackground(isSelected);

    }

    public abstract void bindDataToViewHolder(T item, ViewHolder holder, boolean isSelected);

    public void setDelegate(RecyclerViewAdapterDelegate del) {
        delegate = del;
    }

    @Override
    public boolean onLongClick(View child) {
        RecyclerView recyclerView = (RecyclerView) child.getParent();

        int position = recyclerView.getChildAdapterPosition(child);

        if (position == RecyclerView.NO_POSITION) {
            return false;
        }

        T item = getItemAtPosition(position);

        if (mMultiSelectionManager.isSelectable(item)) {
            mMultiSelectionManager.toggleSelected(item);

            setItemToAnimate(item);

            notifyItemChanged(position);

            return true;
        }

        return false;
    }

    protected T getItemAtPosition(int position) {
        return mListItems.get(position);
    }

    public void addItem(T item) {
        mListItems.add(item);

        int pos = mListItems.indexOf(item);
        notifyItemInserted(pos);
    }

    public void removeItem(T item) {
        int pos = mListItems.indexOf(item);
        mListItems.remove(item);
        mMultiSelectionManager.removeItem(item);

        if (shouldAnimateItem(item)) {
            resetItemToAnimate();
        }

        notifyItemRemoved(pos);
    }

    public void itemChanged(T item) {
        if (mListItems.contains(item)) {
            int pos = mListItems.indexOf(item);
            notifyItemChanged(pos);
        }
    }

    public List<T> getItems() {
        return mListItems;
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    public List<T> getSelectedItems() {
        return mMultiSelectionManager.getSelectedItems();
    }

    public void clearSelection() {
        mMultiSelectionManager.clearSelection();
        notifyDataSetChanged();
    }


    private void setItemToAnimate(T item) {
        mItemToAnimate = item;
    }

    private boolean shouldAnimateItem(T item) {
        return mItemToAnimate == item;
    }

    private void resetItemToAnimate() {
        mItemToAnimate = null;
    }


    public void onSelectionChanged(RecyclerViewMultiSelectionManager multiSelectionManager) {
        notifySelectionChanged();
    }

    private void notifySelectionChanged() {
        if (delegate != null) {
            delegate.onSelectionChanged(this);
        }
    }
}
