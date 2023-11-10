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
import androidx.test.espresso.contrib.RecyclerViewActions;
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
    public void Additem(){
        // Click on Add Item Button
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Computer"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Lenovo"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("23.20"));
        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        //onView(withText("OK")).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));









// Check if text "Edmonton" is matched with any of the text

        //onView(withText("Edmonton")).check(matches(isDisplayed()));

    }

    @Test

    public void deleteitem(){

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        onView(withId(R.id.delete_button)).perform(click());

    }

    @Test
    public void AddMultipleItems(){
        // Click on Add Item Button
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Computer"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Lenovo"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("23.20"));
        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        // Add second object
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Hardrive"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2003"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("09"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("Lenovo"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("23.20"));
        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        // Add third object
        onView(withId(R.id.add_item_button)).perform(click());
        //Type in an item into the dialog
        onView(withId(R.id.item_name_edit_text)).perform(ViewActions.typeText("Chair"));
        onView(withId(R.id.purchase_year_edit_text)).perform(ViewActions.typeText("2023"));
        onView(withId(R.id.purchase_month_edit_text)).perform(ViewActions.typeText("07"));
        onView(withId(R.id.description_edit_text)).perform(ViewActions.typeText("John cena"));
        onView(withId(R.id.make_edit_text)).perform(ViewActions.typeText("12"));
        onView(withId(R.id.model_edit_text)).perform(ViewActions.typeText("6.8"));
        onView(withId(R.id.serial_number_edit_text)).perform(ViewActions.typeText("985531454"));
        onView(withId(R.id.estimated_value_edit_text)).perform(ViewActions.typeText("25"));
        //onView(withId(R.id.comment_edit_text)).perform(ViewActions.typeText("Its a good laptop"));
        onView(withId(android.R.id.button1)).perform(click());
        //onView(withText("OK")).perform(click());

        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardrive")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));




    }

    @Test
    public void CheckConcistencyAndMultipleDelete(){

        // check if items from previous checks present
        onView(withText("Computer")).check(matches(isDisplayed()));
        onView(withText("Hardriver")).check(matches(isDisplayed()));
        onView(withText("Chair")).check(matches(isDisplayed()));




    }







    @Test
    public void AddTag() {
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
        onView(withId(android.R.id.button1)).perform(click());

        // Click on OK
//        onView(withText("OK")).inRoot(RootMatchers.isDialog())
//                .check(matches(ViewMatchers.isDisplayed()))
//                .perform(click());
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        onView(withId(R.id.mutliple_tag_button)).perform(click());
        onView(withId(R.id.tag_input)).perform(ViewActions.typeText("Tag1"));
        onView(withId(android.R.id.button1)).perform(click());



        // Check if text "Laptop" is matched with any of the text displayed on the screen
        onView(withText("Laptop")).check(matches(isDisplayed()));
    }

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
