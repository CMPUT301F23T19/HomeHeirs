package com.example.homeheirs;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.fragment.app.Fragment;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Class Responsible for UI testing the add fragment
 * @author : Arsalan
 * Source : https://stackoverflow.com/questions/31394569/how-to-assert-inside-a-recyclerview-in-espresso
 * In Order for proper fuctionality, the database must be empty vefore initiating tests
 *
 * WARNING: FOR THESE TESTS TO WORK, PLEASE LOG INTO THE ACCOUNT, MAKE SURE ALL ITEMS ARE DELETED, AND STAY LOGGED IN
 *
 * Method fuctionality is indicated by thier name
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddTagFragmentTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testEmptyTag() {
        // Click on Add Item Button
        onView(withId(R.id.add_item_button)).perform(click());

        // Type in item information into the Dialog Interface
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Laptop"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("04"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("5"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("gaming laptop"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("Dell"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("XPS 15"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("123456"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("1500"), ViewActions.closeSoftKeyboard());

        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        onView(withId(R.id.mutliple_tag_button)).perform(click());
        onView(withId(R.id.tag_input)).perform(ViewActions.typeText(""));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.tag_input)).check(matches(hasErrorText("Field Can't be Empty")));
    }
}
