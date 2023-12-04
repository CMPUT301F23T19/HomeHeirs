package com.example.homeheirs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;



        import org.junit.Before;
        import org.junit.Test;
        import static org.junit.Assert.*;

public class addItemFragmentUnitTest {

    private AddItemFragment addItemFragment;

    @Before
    public void setUp() {
        addItemFragment = new AddItemFragment();
    }

    @Test
    public void isValidInput_ValidInputs_ReturnsTrue() {
        assertTrue(addItemFragment.isValidInput("Test Item", 5, 10, 2023, "A description", "Make", "Model", 12345, 100.0, "Comment"));
    }

    @Test
    public void isValidInput_EmptyName_ReturnsFalse() {
        assertFalse(addItemFragment.isValidInput("", 5, 10, 2023, "A description", "Make", "Model", 12345, 100.0, "Comment"));
    }

    @Test
    public void isValidInput_InvalidMonth_ReturnsFalse() {
        assertFalse(addItemFragment.isValidInput("Test Item", 13, 10, 2023, "A description", "Make", "Model", 12345, 100.0, "Comment"));
    }

    // Additional test cases for other invalid inputs
    // ...
}