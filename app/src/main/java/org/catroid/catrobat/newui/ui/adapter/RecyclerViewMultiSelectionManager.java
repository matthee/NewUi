package org.catroid.catrobat.newui.ui.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecyclerViewMultiSelectionManager<T> {
    private Set<T> mSelectedItems;
    private RecyclerViewMultiSelectionManagerDelegate mDelegate;

    public RecyclerViewMultiSelectionManager() {
        mSelectedItems = new HashSet<T>();
    }

    public List<T> getSelectedItems() {
        return new ArrayList<>(mSelectedItems);
    }

    public void setSelected(T item, boolean selected) {
        if (selected) {
            if (!mSelectedItems.contains(item)) {
                mSelectedItems.add(item);
                notifySelectionChanged();
            }

        } else {
            if (mSelectedItems.contains(item)) {
                mSelectedItems.remove(item);
                notifySelectionChanged();
            }
        }
    }


    public void setDelegate(RecyclerViewMultiSelectionManagerDelegate delegate) {
        mDelegate = delegate;
    }

    public boolean getIsSelected(T item) {
        return mSelectedItems.contains(item);
    }

    public void clearSelection() {
        mSelectedItems.clear();
        notifySelectionChanged();
    }

    public boolean isSelectable(T item) {
        return true;
    }

    public void toggleSelected(T item) {
        setSelected(item, !getIsSelected(item));
    }

    public void removeItem(T item) {
        setSelected(item, false);
    }

    private void notifySelectionChanged() {
        if (mDelegate != null) {
            mDelegate.onSelectionChanged(this);
        }
    }
}
