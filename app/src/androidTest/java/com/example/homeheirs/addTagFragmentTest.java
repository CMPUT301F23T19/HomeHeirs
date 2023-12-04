package com.example.homeheirs;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class addTagFragmentTest {

    @Before
    public void setUp() {
        FragmentScenario.launchInContainer(AddTagFragment.class);
    }

    @Test
    public void testValidInput_SubmitsSuccessfully() {
        // Enter a valid tag name
        onView(withId(R.id.tag_input)).perform(typeText("New Tag"), closeSoftKeyboard());

        // Click OK button
        onView(withText("OK")).perform(click());

        // Assertions to verify successful submission can be added here
    }

    @Test
    public void testInvalidInput_ShowsError() {
        // Leave the tag name input empty
        onView(withId(R.id.tag_input)).perform(typeText(""), closeSoftKeyboard());

        // Click OK button
        onView(withText("OK")).perform(click());

        // Verify that an error is shown
        onView(withId(R.id.tag_input)).check(ViewAssertions.matches(ViewMatchers.hasErrorText("Field Can'nt be Empty")));
    }

    @Test
    public void testCancelButton_DismissesDialog() {
        // Click Cancel button
        onView(withText("Cancel")).perform(click());

        // Assertions to verify dialog dismissal can be added here
    }

    // Additional test cases can be added here
}
