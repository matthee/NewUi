package org.catroid.catrobat.newui.fragment;

import android.support.test.espresso.ViewAssertion;

import org.catroid.catrobat.newui.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.catroid.catrobat.newui.fragment.Utils.addNewItemNamed;
import static org.catroid.catrobat.newui.fragment.Utils.checkItemNamedExists;
import static org.catroid.catrobat.newui.fragment.Utils.checkItemNamedNotExists;
import static org.catroid.catrobat.newui.fragment.Utils.selectItemNamed;

public class CommonTests {

    public static void testAddNewItem() {
        addNewItemNamed("new item");

        checkItemNamedExists("new item");
    }

    public static void testAddMultipleItems() {
        addNewItemNamed("item 1");
        addNewItemNamed("item 2");
        addNewItemNamed("item 3");

        checkItemNamedExists("item 1");
        checkItemNamedExists("item 2");
        checkItemNamedExists("item 3");
    }

    public static void testCopyItems() {
        addNewItemNamed("item one");
        addNewItemNamed("item two");
        addNewItemNamed("item three");

        selectItemNamed("item one");
        selectItemNamed("item two");

        onView(withId(R.id.btnCopy)).perform(click());

        // originals
        checkItemNamedExists("item one");
        checkItemNamedExists("item two");
        checkItemNamedExists("item three");

        // copies
        checkItemNamedExists("item one 1");
        checkItemNamedExists("item two 1");
        checkItemNamedNotExists("item three 1");
    }

    public static void testRemoveItems() {
        addNewItemNamed("item one");
        addNewItemNamed("item two");
        addNewItemNamed("item three");

        selectItemNamed("item one");
        selectItemNamed("item three");

        onView(withId(R.id.btnDelete)).perform(click());

        checkItemNamedNotExists("item three");
        checkItemNamedExists("item two");
        checkItemNamedNotExists("item one");
    }

    public static void testEditItems() {
        addNewItemNamed("item one");
        selectItemNamed("item one");

        onView(withId(R.id.btnEdit)).perform(click());
        onView(withId(R.id.input)).perform(clearText());
        onView(withId(R.id.input)).perform(typeText("another item"));
        onView(withText(R.string.dialog_rename_primary_action)).perform(click());

        checkItemNamedNotExists("item one");
        checkItemNamedExists("another item");
    }

    public static void testIncorrectRenaming() {
        addNewItemNamed("item one");
        addNewItemNamed("item two");

        selectItemNamed("item two");
        onView(withId(R.id.btnEdit)).perform(click());
        onView(withId(R.id.input)).perform(clearText());
        onView(withId(R.id.input)).perform(typeText("item one"));
        onView(withText(R.string.dialog_rename_primary_action)).perform(click());

        onView(withText(R.string.error_invalid_item_name)).check(matches(isDisplayed()));
        onView(withText(R.string.cancel)).perform(click());

        checkItemNamedExists("item two");
    }
}
