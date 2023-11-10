package com.example.homeheirs;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.homeheirs.MainActivity;
import com.example.homeheirs.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void AddItem() {
        // Click on Add Item Button
        onView(withId(R.id.add_item_button)).perform(click());

        // Type in item information into the Dialog Interface
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Laptop"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("5"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("gaming laptop"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("Dell"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("XPS 15"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("123456"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("1500"));

        // Click on OK
        onView(withText("OK")).inRoot(RootMatchers.isDialog())
                .check(matches(ViewMatchers.isDisplayed()))
                .perform(click());

        // Check if text "Laptop" is matched with any of the text displayed on the screen
        onView(withText("Laptop")).check(matches(isDisplayed()));
    }

    @Test
    public void DeleteItem(){
        // Click on Add Item Button
        onView(withId(R.id.add_item_button)).perform(click());

        // Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Computer"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("9"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Lenovo"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("23.20"));
        onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));

        // Click on OK
        onView(withText("OK")).perform(click());
    }
}
