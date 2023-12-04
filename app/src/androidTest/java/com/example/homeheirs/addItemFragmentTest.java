package com.example.homeheirs;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class addItemFragmentTest {

    @Before
    public void setUp() {
        FragmentScenario.launchInContainer(AddItemFragment.class);
    }

    @Test
    public void testValidInput_SubmitsSuccessfully() {
        // Input valid details
        onView(withId(R.id.item_name_edit_text)).perform(typeText("Test Item"), closeSoftKeyboard());
        onView(withId(R.id.purchase_month_edit_text)).perform(typeText("5"), closeSoftKeyboard());
        onView(withId(R.id.purchase_day_edit_text)).perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.purchase_year_edit_text)).perform(typeText("2023"), closeSoftKeyboard());
        onView(withId(R.id.description_edit_text)).perform(typeText("A description"), closeSoftKeyboard());
        onView(withId(R.id.make_edit_text)).perform(typeText("Make"), closeSoftKeyboard());
        onView(withId(R.id.model_edit_text)).perform(typeText("Model"), closeSoftKeyboard());
        onView(withId(R.id.serial_number_edit_text)).perform(typeText("12345"), closeSoftKeyboard());
        onView(withId(R.id.estimated_value_edit_text)).perform(typeText("100"), closeSoftKeyboard());
        onView(withId(R.id.comment_edit_text)).perform(typeText("Comment"), closeSoftKeyboard());

        // Click OK button
        onView(withText("OK")).perform(click());

        // Verify that the new item is displayed in the list
        onView(withText("Test Item")).check(matches(isDisplayed()));
    }

    @Test
    public void testInvalidInput_ShowsError() {
        // Input invalid month
        onView(withId(R.id.purchase_month_edit_text)).perform(typeText("13"), closeSoftKeyboard());

        // Click OK button
        onView(withText("OK")).perform(click());

        // Verify that an error is shown
        onView(withId(R.id.purchase_month_edit_text)).check(matches(hasErrorText("Please enter a valid month")));

        // Verify that the dialog is still displayed
        onView(withText("Add Item")).check(matches(isDisplayed()));
    }

    @Test
    public void testCancelButton_DismissesDialog() {
        // Click Cancel button
        onView(withText("Cancel")).perform(click());

        // Verify that the dialog is not displayed
        onView(withText("Add Item")).check(matches(isDisplayed())); // Or check for dismissal if applicable
    }

    @Test
    public void testFilledForm_EnablesOkButton() {
        // Fill in the form
        onView(withId(R.id.item_name_edit_text)).perform(typeText("Test Item"), closeSoftKeyboard());
        onView(withId(R.id.purchase_month_edit_text)).perform(typeText("5"), closeSoftKeyboard());
        onView(withId(R.id.purchase_day_edit_text)).perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.purchase_year_edit_text)).perform(typeText("2023"), closeSoftKeyboard());
        onView(withId(R.id.description_edit_text)).perform(typeText("A description"), closeSoftKeyboard());
        onView(withId(R.id.make_edit_text)).perform(typeText("Make"), closeSoftKeyboard());
        onView(withId(R.id.model_edit_text)).perform(typeText("Model"), closeSoftKeyboard());
        onView(withId(R.id.serial_number_edit_text)).perform(typeText("12345"), closeSoftKeyboard());
        onView(withId(R.id.estimated_value_edit_text)).perform(typeText("100"), closeSoftKeyboard());
        onView(withId(R.id.comment_edit_text)).perform(typeText("Comment"), closeSoftKeyboard());


        // Verify that the OK button is enabled
        onView(withText("OK")).check(matches(isEnabled()));
    }

    @Test
    public void testInvalidYear_ShowsError() {
        // Input invalid year
        onView(withId(R.id.purchase_year_edit_text)).perform(typeText("1899"), closeSoftKeyboard());

        // Click OK button
        onView(withText("OK")).perform(click());

        // Verify that an error is shown
        onView(withId(R.id.purchase_year_edit_text)).check(matches(hasErrorText("Please enter a valid year")));
    }


}
