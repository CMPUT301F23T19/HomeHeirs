package com.example.homeheirs;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.homeheirs.login;
import com.example.homeheirs.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



/**
 * Class Responsible for UI testing Our Registration activity
 * @author : Arsalan
 * Source : https://stackoverflow.com/questions/31394569/how-to-assert-inside-a-recyclerview-in-espresso
 * In Order for proper fuctionality, the database must be empty vefore initiating tests
 *
 * WARNING: FOR THESE TESTS TO WORK, PLEASE LOG INTO THE ACCOUNT, MAKE SURE ALL ITEMS ARE DELETED, AND LOG OUT
 *
 * Method fuctionality is indicated by thier name
 */

// This requires to be in the Login activity, ie you need to logout
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterTest {



    @Rule
    public ActivityScenarioRule<Register> scenario = new ActivityScenarioRule<Register>(Register.class);


//    @Before
//    public void beforeTest() {
//        boolean isInMainActivity = true;
//
//        try {
//            onView(withText("Username")).check(doesNotExist());
//        } catch (NoMatchingViewException e) {
//            // The view with text "Computer" does not exist, so we are not in the MainActivity
//            isInMainActivity = false;
//        }
//
//        if (isInMainActivity) {
//            // We are in the MainActivity, so perform logout
//            onView(withId(R.id.navigation_logout)).perform(click());
//            onView(withId(R.id.logout_button)).perform(click());
//        }
//
//    }



    @Test
    public void testEmptyUsername() {
        onView(withId(R.id.Username_input_register)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.Password_input_register)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.register_textView)).perform(click());
        onView(withId(R.id.Username_input_register)).check(matches(hasErrorText("Field Can't be Empty")));
        // onView(withId(R.id.Password_input)).check(matches(hasErrorText("Field Can't be Empty")));
    }


    @Test
    public void testEmptyPasswords() {
        onView(withId(R.id.Username_input_register)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.Password_input_register)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.ConfirmPassword_input_register)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.register_textView)).perform(click());
        onView(withId(R.id.Password_input_register)).check(matches(hasErrorText("Field Can't be Empty")));
        // onView(withId(R.id.Password_input)).check(matches(hasErrorText("Field Can't be Empty")));
    }


    @Test
    public void testEmptyDifferentPasswords() {
        onView(withId(R.id.Username_input_register)).perform(typeText("arbitrary@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.Password_input_register)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.ConfirmPassword_input_register)).perform(typeText("654321"), closeSoftKeyboard());
        onView(withId(R.id.register_textView)).perform(click());
        onView(withId(R.id.Password_input_register)).check(matches(hasErrorText("Passwords must be same")));
        // onView(withId(R.id.Password_input)).check(matches(hasErrorText("Field Can't be Empty")));
    }


    @Test
    public void testRegisterExistingUser() {
        onView(withId(R.id.Username_input_register)).perform(typeText("ui@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.Password_input_register)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.ConfirmPassword_input_register)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.register_textView)).perform(click());
        onView(withId(R.id.Username_input_register)).perform(click());
        onView(withId(R.id.Username_input_register)).check(matches(hasErrorText("A User with this Email Already Exists")));
        // onView(withId(R.id.Password_input)).check(matches(hasErrorText("Field Can't be Empty")));
    }


    @Test
    public void testPasswordLength() {
        onView(withId(R.id.Username_input_register)).perform(typeText("hihihi@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.Password_input_register)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.ConfirmPassword_input_register)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.register_textView)).perform(click());
        onView(withId(R.id.Password_input_register)).perform(click());
        onView(withId(R.id.Password_input_register)).check(matches(hasErrorText("Password must be greater then 6 Characters")));
        // onView(withId(R.id.Password_input)).check(matches(hasErrorText("Field Can't be Empty")));
    }


    @Test
    public void testRegisterNewUser() {
        onView(withId(R.id.Username_input_register)).perform(typeText("uiuiui@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.Password_input_register)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.ConfirmPassword_input_register)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.register_textView)).perform(click());

        // double check this method


        // onView(withId(R.id.Password_input)).check(matches(hasErrorText("Field Can't be Empty")));
    }










}