package com.example.homeheirs;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShowItemActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new
            ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void testAddItem() {
        // Click on Add Item Button
        onView(withId(R.id.add_item_button)).perform(click());

        // Type the following details
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Phone"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("10"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("My Phone"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("iPhone"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("13"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("123"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("2000"));
        onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Good Phone"));

        // Click on the positive button of the dialog fragment
        onView(withText("OK")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        onView(withText("Phone")).check(matches(isDisplayed()));
    }

    @Test
    public void testSwitchToShowActivity() {
        // Add a city
        onView(withId(R.id.add_item_button)).perform(click());

        // Type the following details
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Phone"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("10"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("My Phone"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("iPhone"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("13"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("123"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("2000"));
        onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Good Phone"));

        // Confirm Add
        onView(withText("OK")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        // Click on the city in the list
        onView(withId(R.id.item_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Check if ShowActivity is displayed
        onView(withId(R.id.show_item_details)).check(matches(isDisplayed()));
    }


}
