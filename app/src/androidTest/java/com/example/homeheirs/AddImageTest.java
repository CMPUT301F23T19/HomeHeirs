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
 * Class Responsible for UI testing the for testing our image adding and making sure its consistent
 * @author : Arsalan
 * Source : https://stackoverflow.com/questions/31394569/how-to-assert-inside-a-recyclerview-in-espresso
 * In Order for proper fuctionality, the database must be empty vefore initiating tests
 *
 * WARNING: FOR THESE TESTS TO WORK, PLEASE LOG INTO THE ACCOUNT, MAKE SURE ALL ITEMS ARE DELETED, AND LOG OUT
 *
 *
 * WARNING: FOR THESE TESTS A USER MUST BE AVAILABLE TO CLICK AND SELECT AND ITEM FROM THE CAMERA, OR TAKE A CAMERA PICTURE
 *
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

    /**
     * Method that attempts to add a single image using the photoGallery
     * USER MUST BE AVAILABLE TO CLICK AND SELECT IMAGE FROM GALLERY WITH CERTAIN TIME FRAME
     */
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


    /**
     * Method that attempts to add a single image using the camera
     * USER MUST BE AVAILABLE TO CAMPURE IMAGE WITHING CERTAIN TIME FRAME
     */
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



    /**
     * Method that attempts to delete a picture
     * USER MUST BE AVAILABLE TO SELECT IMAGE FROM GALLERY WITHIN CERTAIN TIME FRAME
     */

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






}
