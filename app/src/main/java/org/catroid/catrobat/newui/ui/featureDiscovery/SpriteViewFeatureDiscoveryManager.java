package org.catroid.catrobat.newui.ui.featureDiscovery;

import android.support.v7.app.AppCompatActivity;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import org.catroid.catrobat.newui.ui.fragment.BaseRecyclerListFragment;
import org.catroid.catrobat.newui.ui.fragment.BaseRecyclerListFragmentObserver;

public class SpriteViewFeatureDiscoveryManager implements BaseRecyclerListFragmentObserver {
    private BaseRecyclerListFragment mFragment;

    private static String TAG = "SVFDM";

    public static SpriteViewFeatureDiscoveryManager create(BaseRecyclerListFragment fragment) {
        SpriteViewFeatureDiscoveryManager manager = new SpriteViewFeatureDiscoveryManager();
        manager.setFragment(fragment);

        return manager;
    }

    public void setFragment(BaseRecyclerListFragment fragment) {
        this.mFragment = fragment;
    }

    public void start() {
        mFragment.addObserver(this);

        startAddButtonSequence();
    }


    private void done() {
        mFragment.removeObserver(this);
    }

    @Override
    public void onNewItemAdded(BaseRecyclerListFragment fragment, Object item) {
        done();
    }

    private AppCompatActivity getActivity() {
        return (AppCompatActivity) mFragment.getActivity();
    }

    private void startAddButtonSequence() {
        TapTarget fabTapTarget = SpriteViewFeatureDiscoveryFactory.createFABTapTarget(getActivity());

        TapTargetSequence sequence = new TapTargetSequence(getActivity());
        sequence.target(fabTapTarget).listener(new TapTargetSequence.Listener() {
            @Override
            public void onSequenceFinish() {
                mFragment.onAddButtonClicked();
            }

            @Override
            public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

            }

            @Override
            public void onSequenceCanceled(TapTarget lastTarget) {

            }
        });

        sequence.start();
    }
}
