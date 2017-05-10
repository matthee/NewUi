package org.catroid.catrobat.newui;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.InputType;
import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.ui.SpriteActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RecyclerViewRenameTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("org.catroid.catrobat.newui", appContext.getPackageName());
    }
    @Rule
    public ActivityTestRule<SpriteActivity> activityRule = new ActivityTestRule<>(SpriteActivity.class);

    @Test
    public void testCorrectRenaming()
    {
        onView(withText(R.string.title_activity_recycle_view)).perform(click());
        // create new item
        onView(withId(R.id.fab)).perform(click());
        onView(withInputType(InputType.TYPE_CLASS_TEXT)).perform(typeText("Item1"));
        onView(withText(R.string.dialog_create_item_primary_action)).perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(withInputType(InputType.TYPE_CLASS_TEXT)).perform(typeText("Item2"));
        onView(withText(R.string.dialog_create_item_primary_action)).perform(click());
        // test
        onView(withText("Item1")).check(matches(isDisplayed()));

        onView(withText("Item1")).perform(longClick());

        onView(withId(R.id.btnEdit)).perform(click());
        onView(withInputType(InputType.TYPE_CLASS_TEXT)).perform(typeText("Test"));
        onView(withText(R.string.dialog_rename_primary_action)).perform(click());

        onView(withText("Test")).check(matches(isDisplayed()));
    }
    @Test
    public void testIncorrectRenaming()
    {
        onView(withText(R.string.title_activity_recycle_view)).perform(click());
        // create new item
        onView(withId(R.id.fab)).perform(click());
        onView(withInputType(InputType.TYPE_CLASS_TEXT)).perform(typeText("Item1"));
        onView(withText(R.string.dialog_create_item_primary_action)).perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(withInputType(InputType.TYPE_CLASS_TEXT)).perform(typeText("Item2"));
        onView(withText(R.string.dialog_create_item_primary_action)).perform(click());

        onView(withText("Item1")).check(matches(isDisplayed()));

        onView(withText("Item1")).perform(longClick());
        onView(withId(R.id.btnEdit)).perform(click());
        onView(withInputType(InputType.TYPE_CLASS_TEXT)).perform(typeText("Item1"));
        onView(withText(R.string.dialog_rename_primary_action)).perform(click());
        onView(withText(R.string.dialog_rename_item)).check(matches(isDisplayed()));

        onView(withInputType(InputType.TYPE_CLASS_TEXT)).perform(clearText());
        onView(withInputType(InputType.TYPE_CLASS_TEXT)).perform(typeText("Item2"));
        onView(withText(R.string.dialog_rename_primary_action)).perform(click());
        onView(withText(R.string.dialog_rename_item)).check(matches(isDisplayed()));
    }
}