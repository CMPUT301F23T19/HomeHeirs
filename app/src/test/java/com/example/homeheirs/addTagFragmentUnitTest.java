package com.example.homeheirs;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class addTagFragmentUnitTest {

    private AddTagFragment addTagFragment;

    @Before
    public void setUp() {
        addTagFragment = new AddTagFragment();
    }

    @Test
    public void isTagNameValid_ValidName_ReturnsTrue() {
        assertTrue(addTagFragment.isTagNameValid("New Tag"));
    }

    @Test
    public void isTagNameValid_EmptyName_ReturnsFalse() {
        assertFalse(addTagFragment.isTagNameValid(""));
    }

    // Additional test cases for other scenarios
}
