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

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.homeheirs.login;
import com.example.homeheirs.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;





@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {

    @Rule
    public ActivityScenarioRule<login> scenario = new ActivityScenarioRule<login>(login.class);

    @Test
    public void testEmptyUsernameAndPassword() {
        onView(withId(R.id.Username_input)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.Password_input)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.ok_textView)).perform(click());
        onView(withId(R.id.Username_input)).check(matches(hasErrorText("Field Can't be Empty")));
        onView(withId(R.id.Password_input)).check(matches(hasErrorText("Field Can't be Empty")));
    }

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
        onView(withId(R.id.Username_input)).check(matches(hasErrorText("Wrong username or Password")));
    }

    @Test
    public void testNavigationToRegistration() {
        onView(withId(R.id.registration_textview)).perform(click());
        // Check if the Registration activity was started
    }
}
