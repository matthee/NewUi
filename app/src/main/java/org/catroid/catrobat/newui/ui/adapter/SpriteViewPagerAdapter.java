package org.catroid.catrobat.newui.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import org.catroid.catrobat.newui.ui.featureDiscovery.SpriteViewFeatureDiscoveryFactory;
import org.catroid.catrobat.newui.ui.fragment.BaseRecyclerListFragment;
import org.catroid.catrobat.newui.ui.fragment.LookListFragment;
import org.catroid.catrobat.newui.ui.fragment.SoundListFragment;

import java.util.ArrayList;
import java.util.List;

public class SpriteViewPagerAdapter extends FragmentPagerAdapter {
    private int mCurrentPosition;

    private List<BaseRecyclerListFragment> mFragments = new ArrayList<>();
    private AppCompatActivity mActivity;

    public SpriteViewPagerAdapter(AppCompatActivity activity) {
        super(activity.getSupportFragmentManager());
        mActivity = activity;

        setupFragments();
    }

    private void setupFragments() {
        mFragments.add(LookListFragment.newInstance(0));
        mFragments.add(SoundListFragment.newInstance(1));
    }

    @Override
    public Fragment getItem(int position) {
        return getBaseRecyclerListFragmentAtPosition(position);
    }

    private BaseRecyclerListFragment getBaseRecyclerListFragmentAtPosition(int position) {
        if (0 <= position && position < mFragments.size()) {
            return mFragments.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        BaseRecyclerListFragment fragment = getBaseRecyclerListFragmentAtPosition(position);

        if (fragment != null) {
            int tabNameResource = fragment.getTabNameResource();

            return mActivity.getString(tabNameResource);
        } else {
            return null;
        }
    }

    public void onPageSelected(int position) {
        mCurrentPosition = position;

        clearSelectedItems();
    }

    public void onAddButtonClicked() {
        BaseRecyclerListFragment fragment = getCurrentFragment();

        fragment.onAddButtonClicked();
    }

    protected BaseRecyclerListFragment getCurrentFragment() {
        return (BaseRecyclerListFragment) getItem(mCurrentPosition);
    }

    protected void clearSelectedItems() {
        for (BaseRecyclerListFragment fragment : mFragments) {
            fragment.clearSelection();
        }
    }
}
