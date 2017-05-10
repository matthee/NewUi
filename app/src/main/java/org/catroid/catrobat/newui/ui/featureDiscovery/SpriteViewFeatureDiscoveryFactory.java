package org.catroid.catrobat.newui.ui.featureDiscovery;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.ui.fragment.LookListFragment;

public class SpriteViewFeatureDiscoveryFactory {
    public static TapTarget createLooksTapTarget(AppCompatActivity activity) {

        TabLayout tabLayoutView = (TabLayout) activity.findViewById(R.id.tab_layout);

        View target = ((ViewGroup) tabLayoutView.getChildAt(0)).getChildAt(0);

        return TapTarget.forView(target, "We've got Targets!", "We've got the BEST targets!")
                .outerCircleColor(R.color.colorPrimary)
                .outerCircleAlpha(0.96f)
                .targetCircleColor(R.color.colorPrimary)
                .textColor(R.color.white)
                .textTypeface(Typeface.SANS_SERIF)
                .descriptionTextColor(R.color.white)
                .dimColor(R.color.white)
                .drawShadow(true)
                .cancelable(false)
                .transparentTarget(true)
                .tintTarget(true)
                .targetRadius(80);
    }

    public static TapTarget createFABTapTarget(AppCompatActivity activity) {
        return TapTarget.forView(activity.findViewById(R.id.fab), "Adding an Item", "To add an item press the \"+\" button")
                        .outerCircleColor(R.color.colorPrimary)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.colorAccent)
                        .textColor(R.color.white)
                        .textTypeface(Typeface.SANS_SERIF)
                        .descriptionTextColor(R.color.white)
                        .dimColor(R.color.white)
                        .drawShadow(true)
                        .cancelable(false)
                        .transparentTarget(true)
                        .targetRadius(20);
    }
}
