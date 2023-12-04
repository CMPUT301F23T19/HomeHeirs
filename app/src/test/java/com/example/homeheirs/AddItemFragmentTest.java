package com.example.homeheirs;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.homeheirs.AddItemFragment;

public class AddItemFragmentTest {
    private AddItemFragment addItemFragment;

    @Before
    public void setUp() {
        addItemFragment = new AddItemFragment();
    }

    @Test
    public void testValidate_ValidInput_ReturnsTrue() {
        assertTrue(addItemFragment.validate("ValidName", "1", "2", "2023", "ValidMake", "ValidModel", "123", "50.0"));
    }

//    @Test
//    public void testValidate_InvalidName_ReturnsFalse() {
//        assertFalse(addItemFragment.validate("", "1", "2", "2023", "ValidMake", "ValidModel", "123", "50.0"));
//    }

//    @Test
//    public void testValidate_InvalidMonth_ReturnsFalse() {
//        // Arrange
//        AddItemFragment addItemFragment = new AddItemFragment();
//        int month = 13;  // Invalid month
//
//        // Act
//        boolean result = addItemFragment.validate("Test Item", month, 15, 2023, "Test Description", "Test Make", "Test Model", 12345, 100.0, "Additional details");
//
//        // Assert
//        assertFalse(result);
//    }
//
//    @Test
//    public void testValidate_InvalidDay_ReturnsFalse() {
//        // Arrange
//        AddItemFragment addItemFragment = new AddItemFragment();
//        int day = 32;  // Invalid day
//
//        // Act
//        boolean result = addItemFragment.validate("Test Item", 6, day, 2023, "Test Description", "Test Make", "Test Model", 12345, 100.0, "Additional details");
//
//        // Assert
//        assertFalse(result);
//    }
//
//    @Test
//    public void testValidate_InvalidYear_ReturnsFalse() {
//        // Arrange
//        AddItemFragment addItemFragment = new AddItemFragment();
//        int year = 2036;  // Invalid year
//
//        // Act
//        boolean result = addItemFragment.validate("Test Item", 6, 15, year, "Test Description", "Test Make", "Test Model", 12345, 100.0, "Additional details");
//
//        // Assert
//        assertFalse(result);
//    }
//
//    @Test
//    public void testValidate_InvalidMake_ReturnsFalse() {
//        // Arrange
//        AddItemFragment addItemFragment = new AddItemFragment();
//        String make = "";  // Empty make
//
//        // Act
//        boolean result = addItemFragment.validate("Test Item", 6, 15, 2023, "Test Description", make, "Test Model", 12345, 100.0, "Additional details");
//
//        // Assert
//        assertFalse(result);
//    }
//
//    @Test
//    public void testValidate_InvalidModel_ReturnsFalse() {
//        // Arrange
//        AddItemFragment addItemFragment = new AddItemFragment();
//        String model = null;  // Null model
//
//        // Act
//        boolean result = addItemFragment.validate("Test Item", 6, 15, 2023, "Test Description", "Test Make", model, 12345, 100.0, "Additional details");
//
//        // Assert
//        assertFalse(result);
//    }
//
//    @Test
//    public void testValidate_InvalidValue_ReturnsFalse() {
//        // Arrange
//        AddItemFragment addItemFragment = new AddItemFragment();
//        double value = -50.0;  // Negative value
//
//        // Act
//        boolean result = addItemFragment.validate("Test Item", 6, 15, 2023, "Test Description", "Test Make", "Test Model", 12345, value, "Additional details");
//
//        // Assert
//        assertFalse(result);
//    }

    // Add more test cases for other validation rules...

}
