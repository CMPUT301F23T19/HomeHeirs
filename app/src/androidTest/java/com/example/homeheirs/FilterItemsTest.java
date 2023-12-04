package com.example.homeheirs;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anything;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
/**
 * Class Responsible for intent testing
 * @author : Arsalan
 * Source : https://stackoverflow.com/questions/31394569/how-to-assert-inside-a-recyclerview-in-espresso
 * In Order for proper fuctionality, the database must be empty vefore initiating tests
 * The method CheckConcistencyAndMultipleDelete - sometimes fails or passes - looks like it depends on speed but
 * will be checking later
 * Method fuctionality is indicated by thier name
 */

public class FilterItemsTest {

    @Rule
    public ActivityScenarioRule<login> scenario = new ActivityScenarioRule<login>(login.class);

    @Before
    public void setUp() throws InterruptedException {



        onView(withId(R.id.Username_input)).perform(ViewActions.typeText("hello@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.Password_input)).perform(ViewActions.typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.ok_textView)).perform(click());

        // Wait for the login

        Thread.sleep(5000);

    }

    @After
    public void tearDown() {
        // Log out
        onView(withId(R.id.navigation_logout)).perform(click());
        onView(withId(R.id.logout_button)).perform(click());

        // Additional actions if needed after logout
    }

    @Test
    public void FilterDate(){

        // Click on Add Item Button
        onView(withId(R.id.add_item_button)).perform(click());

        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Computer"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("04"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Lenovo"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("23.20"),closeSoftKeyboard());

        onView(withId(android.R.id.button1)).perform(click());

        // Add second object
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Hardrive"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2003"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("8"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Seagate"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("23.20"),closeSoftKeyboard());

        onView(withId(android.R.id.button1)).perform(click());

        // Add third object
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Chair"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("10"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("07"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("LenovoSpecial"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("25"),closeSoftKeyboard());

        onView(withId(android.R.id.button1)).perform(click());
        //onView(withText("OK")).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.navigation_filter)).perform(click());
        onView(withId(R.id.dateFilterButton)).perform(click());

        onView(withText("Tag2")).perform(click());
        onView(withText("Apply")).perform(click());

        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.tagsFilterButton)).perform(click());
        onView(withText("Tag1")).perform(click());
        onView(withText("Apply")).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));

        onView(withId(R.id.allFilterButton)).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(withId(R.id.delete_button)).perform(click());

        onView(withText("Computer")).check(doesNotExist());
        onView(withText("Hardrive")).check(doesNotExist());
        onView(withText("Chair")).check(doesNotExist());
    }

    @Test
    public void FilterbyDescription(){


        // Click on Add Item Button
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Computer"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("04"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Lenovo"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("23.20"),closeSoftKeyboard());

        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        // Add second object
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Hardrive"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2003"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("8"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Seagate"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("23.20"),closeSoftKeyboard());

        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        // Add third object
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Chair"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("10"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("07"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("LenovoSpecial"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("25"),closeSoftKeyboard());

        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        //onView(withText("OK")).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));


        onView(withId(R.id.navigation_filter)).perform(click());

        onView(withId(R.id.descFilterButton)).perform(click());

        onView(withId(R.id.list_search_view)).perform(ViewActions.typeText("Le"),closeSoftKeyboard());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onView(withText("Computer")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());



        onView(withId(R.id.allFilterButton)).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));


        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(withId(R.id.delete_button)).perform(click());

        onView(withText("Computer")).check(doesNotExist());
        onView(withText("Hardrive")).check(doesNotExist());
        onView(withText("Chair")).check(doesNotExist());

    }


    @Test
    public void FilterbyMake(){


        // Click on Add Item Button
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Computer"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("04"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Lenovo"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("23.20"),closeSoftKeyboard());

        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        // Add second object
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Hardrive"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2003"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("8"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Seagate"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("31"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("23.20"),closeSoftKeyboard());

        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        // Add third object
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Chair"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("10"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("07"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("LenovoSpecial"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("123"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("25"),closeSoftKeyboard());

        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        //onView(withText("OK")).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));


        onView(withId(R.id.navigation_filter)).perform(click());

        onView(withId(R.id.makeFilterButton)).perform(click());

        onView(withId(R.id.list_search_view)).perform(ViewActions.typeText("12"),closeSoftKeyboard());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onView(withText("Computer")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.list_search_view)).perform(ViewActions.typeText("3"),closeSoftKeyboard());

        onView(withText("Computer")).check(doesNotExist());
        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.allFilterButton)).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(withId(R.id.delete_button)).perform(click());

        onView(withText("Computer")).check(doesNotExist());
        onView(withText("Hardrive")).check(doesNotExist());
        onView(withText("Chair")).check(doesNotExist());

    }

    @Test
    public void FilterTags(){

        // Click on Add Item Button
        onView(withId(R.id.add_item_button)).perform(click());

        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Computer"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("04"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Lenovo"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("23.20"),closeSoftKeyboard());

        onView(withId(android.R.id.button1)).perform(click());

        // Add second object
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Hardrive"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2003"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("8"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Seagate"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("23.20"),closeSoftKeyboard());

        onView(withId(android.R.id.button1)).perform(click());

        // Add third object
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Chair"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("10"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("07"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("LenovoSpecial"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("25"),closeSoftKeyboard());

        onView(withId(android.R.id.button1)).perform(click());
        //onView(withText("OK")).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        onView(withId(R.id.mutliple_tag_button)).perform(click());
        onView(withId(R.id.tag_input)).perform(ViewActions.typeText("Tag1"));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, longClick()));

        onView(withId(R.id.mutliple_tag_button)).perform(click());
        onView(withId(R.id.tag_input)).perform(ViewActions.typeText("Tag2"));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, longClick()));

        onView(withId(R.id.mutliple_tag_button)).perform(click());
        onView(withId(R.id.tag_input)).perform(ViewActions.typeText("Tag2"));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.navigation_filter)).perform(click());
        onView(withId(R.id.tagsFilterButton)).perform(click());
        onView(withText("Tag2")).perform(click());
        onView(withText("Apply")).perform(click());

        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.tagsFilterButton)).perform(click());
        onView(withText("Tag1")).perform(click());
        onView(withText("Apply")).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));

        onView(withId(R.id.allFilterButton)).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(withId(R.id.delete_button)).perform(click());

        onView(withText("Computer")).check(doesNotExist());
        onView(withText("Hardrive")).check(doesNotExist());
        onView(withText("Chair")).check(doesNotExist());
    }
}
