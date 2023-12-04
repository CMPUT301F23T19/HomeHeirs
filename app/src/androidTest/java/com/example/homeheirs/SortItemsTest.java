package com.example.homeheirs;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;




import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anything;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.homeheirs.MainActivity;
import com.example.homeheirs.R;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
/**
 * Class Responsible for UI testing Our Sorting Feature
 * @author : Arsalan
 * Source : https://stackoverflow.com/questions/31394569/how-to-assert-inside-a-recyclerview-in-espresso
 * In Order for proper fuctionality, the database must be empty vefore initiating tests
 *
 * WARNING: FOR THESE TESTS TO WORK, PLEASE LOG INTO THE ACCOUNT, MAKE SURE ALL ITEMS ARE DELETED, AND LOG OUT
 *
 * Method fuctionality is indicated by thier name
 * ALL METHODS TEST BOTH ASCENDING AND DESCENDING
 */

public class SortItemsTest {

    @Rule
    public ActivityScenarioRule<login> scenario = new ActivityScenarioRule<login>(login.class);




    @Before
    public void setUp() throws InterruptedException {



        onView(withId(R.id.Username_input)).perform(ViewActions.typeText("ui@gmail.com"), closeSoftKeyboard());
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




//    @Test
//
//    public void deleteitem(){
//
//        onView(withId(R.id.item_list))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));
//
//        onView(withId(R.id.delete_button)).perform(click());
//
//    }

    @Test
    public void SortbyMake(){


        // Click on Add Item Button
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Computer"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("04"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Lenovo"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("Lenovo Legion"));
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
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("FiveTbVersion"));
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
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("BestChair"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("25"),closeSoftKeyboard());

        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        //onView(withText("OK")).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));


        onView(withId(R.id.sort_item_button)).perform(click());



        onView(withId(R.id.makeASCTapped)).perform(click());


        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));




        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withText("Hardrive")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));


        onView(withText("Computer")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());




        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));



        onView(withId(R.id.makeDESTapped)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onView(withText("Computer")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withText("Hardrive")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));


        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());


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
    public void SortbyValue(){


        // Click on Add Item Button
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Computer"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("04"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Lenovo"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("Lenovo Legion"));
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
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("FiveTbVersion"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("22"),closeSoftKeyboard());

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
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("BestChair"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("18"),closeSoftKeyboard());

        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        //onView(withText("OK")).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));


        onView(withId(R.id.sort_item_button)).perform(click());



        onView(withId(R.id.valueASCTapped)).perform(click());


        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));




        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withText("Hardrive")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));


        onView(withText("Computer")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());




        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));



        onView(withId(R.id.valueDESTapped)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onView(withText("Computer")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withText("Hardrive")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));


        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());


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
    public void SortbyDate(){


        // Click on Add Item Button
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Computer"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("04"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Lenovo"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("Lenovo Legion"));
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
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("FiveTbVersion"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("22"),closeSoftKeyboard());

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
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("BestChair"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("18"),closeSoftKeyboard());

        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        //onView(withText("OK")).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));


        onView(withId(R.id.sort_item_button)).perform(click());



        onView(withId(R.id.dateASCTapped)).perform(click());


        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));




        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onView(withText("Hardrive")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));


        onView(withText("Computer")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());




        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));



        onView(withId(R.id.dateDESTapped)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onView(withText("Computer")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));


        onView(withText("Hardrive")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());


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
    public void SortbyDescription(){


        // Click on Add Item Button
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Computer"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("04"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Lenovo"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("Lenovo Legion"));
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
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("FiveTbVersion"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("22"),closeSoftKeyboard());

        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        // Add third object
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Chair"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("10"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("07"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Hillsbury"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("BestChair"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("18"),closeSoftKeyboard());

        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        //onView(withText("OK")).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));


        onView(withId(R.id.sort_item_button)).perform(click());



        onView(withId(R.id.descriptionASCTapped)).perform(click());


        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));




        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withText("Computer")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));


        onView(withText("Hardrive")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());




        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));



        onView(withId(R.id.descriptionDESTapped)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onView(withText("Hardrive")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withText("Computer")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));


        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());


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
    public void SortbyTags(){


        // Click on Add Item Button
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Computer"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("04"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Lenovo"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("Lenovo Legion"));
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
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("FiveTbVersion"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("22"),closeSoftKeyboard());

        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        // Add third object
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Chair"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("10"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("07"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Hillsbury"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("BestChair"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("18"),closeSoftKeyboard());

        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        //onView(withText("OK")).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));



        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        onView(withId(R.id.mutliple_tag_button)).perform(click());
        onView(withId(R.id.tag_input)).perform(ViewActions.typeText("Electronic"));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, longClick()));

        onView(withId(R.id.mutliple_tag_button)).perform(click());
        onView(withId(R.id.tag_input)).perform(ViewActions.typeText("device"));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, longClick()));

        onView(withId(R.id.mutliple_tag_button)).perform(click());
        onView(withId(R.id.tag_input)).perform(ViewActions.typeText("furniture"));
        onView(withId(android.R.id.button1)).perform(click());







        onView(withId(R.id.sort_item_button)).perform(click());



        onView(withId(R.id.tagsASCTapped)).perform(click());


        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));




        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onView(withText("Hardrive")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withText("Computer")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));


        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());




        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));



        onView(withId(R.id.tagsDESTapped)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onView(withText("Chair")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withText("Computer")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));


        onView(withText("Hardrive")).check(matches(isDisplayed()));

        onView(withId(R.id.back_button)).perform(click());


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











//    @Test
//    public void CheckConcistencyAndMultipleDelete(){
//
//        // check if items from previous checks present
//        onView(withText("Computer")).check(matches(isDisplayed()));
//        onView(withText("Hardrive")).check(matches(isDisplayed()));
//        onView(withText("Chair")).check(matches(isDisplayed()));
//
//        onView(withId(R.id.item_list))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));
//
//        onView(withId(R.id.item_list))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
//        onView(withId(R.id.item_list))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
//
//        onView(withId(R.id.delete_button)).perform(click());
//
//        onView(withText("Computer")).check(doesNotExist());
//        onView(withText("Hardrive")).check(doesNotExist());
//        onView(withText("Chair")).check(doesNotExist());
//
//
//
//
//
//
//
//
//
//
//    }

























//    @Test
//    public void testClearCity(){
//// Add first city to the list
//        onView(withId(R.id.button_add)).perform(click());
//        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
//        onView(withId(R.id.button_confirm)).perform(click());
////Add another city to the list
//        onView(withId(R.id.button_add)).perform(click());
//        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Vancouver"));
//        onView(withId(R.id.button_confirm)).perform(click());
////Clear the list
//        onView(withId(R.id.button_clear)).perform(click());
//        onView(withText("Edmonton")).check(doesNotExist());
//        onView(withText("Vancouver")).check(doesNotExist());
//    }
//
//    @Test
//    public void testListView(){
//// Add a city
//        onView(withId(R.id.button_add)).perform(click());
//        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
//        onView(withId(R.id.button_confirm)).perform(click());
//// Check if in the Adapter view (given id of that adapter view),there is a data
//// (which is an instance of String) located at position zero.
//// If this data matches the text we provided then Voila! Our test should pass
//// You can also use anything() in place of
//        // not right ? is(instanceOf(String.class));
//        onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.city_list
//        )).atPosition(0).check(matches((withText("Edmonton"))));
//    }


}
