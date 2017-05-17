package org.catroid.catrobat.newui.fragment;

import android.support.test.rule.ActivityTestRule;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.ui.SpriteActivity;
import org.catroid.catrobat.newui.utils.BaseActivityInstrumentationRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LookViewInstrumentedTest {
    @Rule
    public ActivityTestRule<SpriteActivity> activityRule =
            new BaseActivityInstrumentationRule<>(SpriteActivity.class);

    @Before
    public void navigateToTab() {
        Utils.navigateToTab(R.string.tab_name_looks);
    }

    @Test
    public void testAddNewItem() {
        CommonTests.testAddNewItem();
    }


    @Test
    public void testAddMultipleItems() {
        CommonTests.testAddMultipleItems();
    }

    @Test
    public void testCopyItems() {
        CommonTests.testCopyItems();
    }

    @Test
    public void testRemoveItems() {
        CommonTests.testRemoveItems();
    }

    @Test
    public void testEditItems() {
        CommonTests.testEditItems();
    }

    @Test
    public void testIncorrectRenaming() {
        CommonTests.testIncorrectRenaming();
    }

}
