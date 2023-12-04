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
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

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
 * Class Responsible for intent testing
 * @author : Arsalan
 * Source : https://stackoverflow.com/questions/31394569/how-to-assert-inside-a-recyclerview-in-espresso
 * In Order for proper fuctionality, the database must be empty vefore initiating tests
 * The method CheckConcistencyAndMultipleDelete - sometimes fails or passes - looks like it depends on speed but
 * will be checking later
 * Method fuctionality is indicated by thier name
 */

public class AddImageTest {

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
    @Test
    public void AddSinglePhotoGallery() throws InterruptedException {
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
        //onView(withText("OK")).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onData(anything())
                .inAdapterView(withId(R.id.photograph_grid))
                .atPosition(0)
                .perform(click());



        onView(withId(R.id.fragment_libray_option)).perform(click());


        // need to manually pick out an image
        Thread.sleep(5000);


        onData(anything())
                .inAdapterView(withId(R.id.photograph_grid))
                .atPosition(1)
                .perform(click());



        onView(withId(R.id.enlarged_imageCancelButton)).perform(click());

        onView(withId(R.id.save_button)).perform(click());

        onView(withId(R.id.back_button)).perform(click());


        onView(withId(R.id.item_list))
               .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onData(anything())
                .inAdapterView(withId(R.id.photograph_grid))
                .atPosition(1)
                .perform(click());

        onView(withId(R.id.enlarged_imageCancelButton)).perform(click());

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));



        onView(withId(R.id.delete_button)).perform(click());












// Check if text "Edmonton" is matched with any of the text

        //onView(withText("Edmonton")).check(matches(isDisplayed()));

    }


    @Test
    public void AddSinglePhotoCamera() throws InterruptedException {
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
        //onView(withText("OK")).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onData(anything())
                .inAdapterView(withId(R.id.photograph_grid))
                .atPosition(0)
                .perform(click());





        onView(withId(R.id.fragment_camera_option)).perform(click());



        // need to manually pick out an image
        Thread.sleep(6000);


        onData(anything())
                .inAdapterView(withId(R.id.photograph_grid))
                .atPosition(1)
                .perform(click());



        onView(withId(R.id.enlarged_imageCancelButton)).perform(click());

        onView(withId(R.id.save_button)).perform(click());

        onView(withId(R.id.back_button)).perform(click());


        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onData(anything())
                .inAdapterView(withId(R.id.photograph_grid))
                .atPosition(1)
                .perform(click());

        onView(withId(R.id.enlarged_imageCancelButton)).perform(click());

        onView(withId(R.id.back_button)).perform(click());

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));



        onView(withId(R.id.delete_button)).perform(click());












// Check if text "Edmonton" is matched with any of the text

        //onView(withText("Edmonton")).check(matches(isDisplayed()));

    }



    @Test
    public void DeletePhoto() throws InterruptedException {
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
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("23.20"), closeSoftKeyboard());

        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        //onView(withText("OK")).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onData(anything())
                .inAdapterView(withId(R.id.photograph_grid))
                .atPosition(0)
                .perform(click());


        onView(withId(R.id.fragment_libray_option)).perform(click());


        // need to manually pick out an image
        Thread.sleep(5000);


        onData(anything())
                .inAdapterView(withId(R.id.photograph_grid))
                .atPosition(1)
                .perform(click());


        onView(withId(R.id.enlarged_imageDeleteButton)).perform(click());

        onView(withId(R.id.save_button)).perform(click());

        onView(withId(R.id.back_button)).perform(click());

        //Thread.sleep(1000);





        onView(withId(R.id.save_button)).perform(click());

        onView(withId(R.id.back_button)).perform(click());


        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));


        onView(withId(R.id.delete_button)).perform(click());
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

//    @Test
//    public void AddMultipleItemsAndDeleteMultiple(){
//        // Click on Add Item Button
//        onView(withId(R.id.add_item_button)).perform(click());
//        //Type in an item into the dialog
//        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Computer"));
//        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
//        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("04"));
//        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
//        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Lenovo"));
//        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
//        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
//        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
//        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("23.20"),closeSoftKeyboard());
//
//        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
//        onView(withId(android.R.id.button1)).perform(click());
//        // Add second object
//        onView(withId(R.id.add_item_button)).perform(click());
//        //Type in an item into the dialog
//        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Hardrive"));
//        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2003"));
//        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("8"));
//        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
//        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Lenovo"));
//        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
//        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
//        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
//        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("23.20"),closeSoftKeyboard());
//
//        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
//        onView(withId(android.R.id.button1)).perform(click());
//        // Add third object
//        onView(withId(R.id.add_item_button)).perform(click());
//        //Type in an item into the dialog
//        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Chair"));
//        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
//        onView(withId(R.id.purchase_day_edit_text)).perform(ViewActions.typeText("10"));
//        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("07"));
//        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("John cena"));
//        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
//        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
//        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
//        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("25"),closeSoftKeyboard());
//
//        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
//        onView(withId(android.R.id.button1)).perform(click());
//        //onView(withText("OK")).perform(click());
//
//        onView(withText("Computer")).check(matches(isDisplayed()));
//        onView(withText("Hardrive")).check(matches(isDisplayed()));
//        onView(withText("Chair")).check(matches(isDisplayed()));
//
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
//    }


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
