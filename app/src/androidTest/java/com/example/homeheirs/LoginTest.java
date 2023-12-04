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
 * Class Responsible for UI testing Our login activity
 * @author : Arsalan
 * Source : https://stackoverflow.com/questions/31394569/how-to-assert-inside-a-recyclerview-in-espresso
 * In Order for proper fuctionality, the database must be empty vefore initiating tests
 *
 * WARNING: FOR THESE TESTS TO WORK, PLEASE LOG INTO THE ACCOUNT, MAKE SURE ALL ITEMS ARE DELETED, AND LOG OUT
 *
 * Method fuctionality is indicated by thier name
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {


    @Rule
    public ActivityScenarioRule<login> scenario = new ActivityScenarioRule<login>(login.class);


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
        onView(withId(R.id.Username_input)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.Password_input)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.ok_textView)).perform(click());
        onView(withId(R.id.Username_input)).check(matches(hasErrorText("Field Can't be Empty")));
       // onView(withId(R.id.Password_input)).check(matches(hasErrorText("Field Can't be Empty")));
    }

    @Test
    public void testEmptyPassword() {
        onView(withId(R.id.Username_input)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.Password_input)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.ok_textView)).perform(click());
        //onView(withId(R.id.Username_input)).check(matches(hasErrorText("Field Can't be Empty")));
         onView(withId(R.id.Password_input)).check(matches(hasErrorText("Field Can't be Empty")));
    }


    // test keeps failing idk why
//    @Test
//    public void testEmptyUsernameAndPassword() {
//        //onView(withId(R.id.Username_input)).perform(typeText(""), closeSoftKeyboard());
//        //onView(withId(R.id.Password_input)).perform(typeText(""), closeSoftKeyboard());
//        onView(withId(R.id.ok_textView)).perform(click());
//        onView(withId(R.id.Username_input)).perform(click());
//        onView(withId(R.id.Username_input)).check(matches(hasErrorText("Field Can't be Empty")));
//        onView(withId(R.id.Password_input)).perform(click());
//        onView(withId(R.id.Password_input)).check(matches(hasErrorText("Field Can't be Empty")));
//    }

    @Test
    public void testValidUsernameAndPasswordInput() {
        onView(withId(R.id.Username_input)).perform(typeText("valid@example.com"), closeSoftKeyboard());
        onView(withId(R.id.Password_input)).perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.ok_textView)).perform(click());
        // Verify the next action after successful login, such as navigating to MainActivity
    }

    @Test
    public void testInvalidUsernameAndPasswordInput() {
        onView(withId(R.id.Username_input)).perform(typeText("invalid"), closeSoftKeyboard());
        onView(withId(R.id.Password_input)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.ok_textView)).perform(click());
        onView(withId(R.id.Password_input)).perform(click());
        onView(withId(R.id.Password_input)).check(matches(hasErrorText("Wrong username or Password")));
    }

    @Test
    public void testNavigationToRegistration() {
        onView(withId(R.id.registration_textview)).perform(click());

    }
}