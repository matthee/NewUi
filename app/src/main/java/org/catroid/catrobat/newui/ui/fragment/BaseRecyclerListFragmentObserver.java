package org.catroid.catrobat.newui.ui.fragment;

public interface BaseRecyclerListFragmentObserver<T> {
    void onNewItemAdded(BaseRecyclerListFragment fragment, T item);
}
