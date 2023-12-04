package com.example.homeheirs;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.fragment.app.Fragment;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Class Responsible for UI testing Our List, ie show details, and adding and deleting multiple items
 * As well as tag adding
 * @author : Arsalan
 * Source : https://stackoverflow.com/questions/31394569/how-to-assert-inside-a-recyclerview-in-espresso
 * In Order for proper fuctionality, the database must be empty vefore initiating tests
 *
 * WARNING: FOR THESE TESTS TO WORK, PLEASE LOG INTO THE ACCOUNT, MAKE SURE ALL ITEMS ARE DELETED STAY LOGGED IN
 *
 * Method fuctionality is indicated by thier name
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddItemFargmentTest {


    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testEmptyName() {
        onView(withId(R.id.add_item_button)).perform(click());

        // Perform actions on the fragment
        onView(withId(R.id.item_name_edit_text)).perform(typeText(""));
        onView(withId(R.id.purchase_month_edit_text)).perform(typeText("02"));
        onView(withId(R.id.purchase_day_edit_text)).perform(typeText("23"));
        onView(withId(R.id.purchase_year_edit_text)).perform(typeText("2023"));
        onView(withId(R.id.make_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.model_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.estimated_value_edit_text)).perform(typeText("50"));

        onView(withText("OK")).perform(click());

        // Check if the appropriate error messages are displayed
        onView(withId(R.id.item_name_edit_text)).perform(click());
        onView(withId(R.id.item_name_edit_text)).check(matches(hasErrorText("Please enter a name")));
    }

    @Test
    public void testEmptyMonth() {

        onView(withId(R.id.add_item_button)).perform(click());

        // Perform actions on the fragment
        onView(withId(R.id.item_name_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.purchase_month_edit_text)).perform(typeText(""));
        onView(withId(R.id.purchase_day_edit_text)).perform(typeText("23"));
        onView(withId(R.id.purchase_year_edit_text)).perform(typeText("2023"));
        onView(withId(R.id.make_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.model_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.estimated_value_edit_text)).perform(typeText("50"));

        onView(withText("OK")).perform(click());

        onView(withId(R.id.purchase_month_edit_text)).perform(click());
        onView(withId(R.id.purchase_month_edit_text)).check(matches(hasErrorText("Please enter a valid month")));
    }

    @Test
    public void testEmptyDay() {
        onView(withId(R.id.add_item_button)).perform(click());

        // Perform actions on the fragment
        onView(withId(R.id.item_name_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.purchase_month_edit_text)).perform(typeText("02"));
        onView(withId(R.id.purchase_day_edit_text)).perform(typeText(""));
        onView(withId(R.id.purchase_year_edit_text)).perform(typeText("2023"));
        onView(withId(R.id.make_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.model_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.estimated_value_edit_text)).perform(typeText("50"));

        onView(withText("OK")).perform(click());

        onView(withId(R.id.purchase_day_edit_text)).perform(click());
        onView(withId(R.id.purchase_day_edit_text)).check(matches(hasErrorText("Please enter a valid day")));
    }


    @Test
    public void testEmptyYear() {

        onView(withId(R.id.add_item_button)).perform(click());

        // Perform actions on the fragment
        onView(withId(R.id.item_name_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.purchase_month_edit_text)).perform(typeText("02"));
        onView(withId(R.id.purchase_day_edit_text)).perform(typeText("23"));
        onView(withId(R.id.purchase_year_edit_text)).perform(typeText("2060"));
        onView(withId(R.id.make_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.model_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.estimated_value_edit_text)).perform(typeText("50"));

        onView(withText("OK")).perform(click());

        onView(withId(R.id.purchase_year_edit_text)).perform(click());
        onView(withId(R.id.purchase_year_edit_text)).check(matches(hasErrorText("Please enter a valid year between 1990 and 2035")));
    }

    @Test
    public void testEmptyMake() {

        onView(withId(R.id.add_item_button)).perform(click());

        onView(withId(R.id.item_name_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.purchase_month_edit_text)).perform(typeText("02"));
        onView(withId(R.id.purchase_day_edit_text)).perform(typeText("23"));
        onView(withId(R.id.purchase_year_edit_text)).perform(typeText("2023"));
        onView(withId(R.id.make_edit_text)).perform(typeText(""));
        onView(withId(R.id.model_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.estimated_value_edit_text)).perform(typeText("50"));

        onView(withText("OK")).perform(click());

        onView(withId(R.id.make_edit_text)).perform(click());
        onView(withId(R.id.make_edit_text)).check(matches(hasErrorText("Please enter a make")));
    }

    @Test
    public void testEmptyModel() {

        onView(withId(R.id.add_item_button)).perform(click());

        // Perform actions on the fragment
        onView(withId(R.id.item_name_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.purchase_month_edit_text)).perform(typeText("02"));
        onView(withId(R.id.purchase_day_edit_text)).perform(typeText("23"));
        onView(withId(R.id.purchase_year_edit_text)).perform(typeText("2023"));
        onView(withId(R.id.make_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.model_edit_text)).perform(typeText(""));
        onView(withId(R.id.estimated_value_edit_text)).perform(typeText("50"));
        onView(withText("OK")).perform(click());

        onView(withId(R.id.model_edit_text)).perform(click());
        onView(withId(R.id.model_edit_text)).check(matches(hasErrorText("Please enter a model")));
    }

    @Test
    public void testInvalidValue() {

        onView(withId(R.id.add_item_button)).perform(click());

        onView(withId(R.id.item_name_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.purchase_month_edit_text)).perform(typeText("02"));
        onView(withId(R.id.purchase_day_edit_text)).perform(typeText("23"));
        onView(withId(R.id.purchase_year_edit_text)).perform(typeText("2023"));
        onView(withId(R.id.make_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.model_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.estimated_value_edit_text)).perform(typeText("-50"));

        onView(withText("OK")).perform(click());

        onView(withId(R.id.serial_number_edit_text)).perform(click());
        onView(withId(R.id.serial_number_edit_text)).check(matches(hasErrorText("Please enter a valid value")));
    }

    @Test
    public void testEmptyValue() {

        onView(withId(R.id.add_item_button)).perform(click());

        onView(withId(R.id.item_name_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.purchase_month_edit_text)).perform(typeText("02"));
        onView(withId(R.id.purchase_day_edit_text)).perform(typeText("23"));
        onView(withId(R.id.purchase_year_edit_text)).perform(typeText("2023"));
        onView(withId(R.id.make_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.model_edit_text)).perform(typeText("Valid"));
        onView(withId(R.id.estimated_value_edit_text)).perform(typeText(""));

        onView(withText("OK")).perform(click());

        onView(withId(R.id.estimated_value_edit_text)).perform(click());
        onView(withId(R.id.estimated_value_edit_text)).check(matches(hasErrorText("Please enter a valid value")));
    }
}
