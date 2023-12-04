package com.example.homeheirs;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.homeheirs.AddItemFragment;

public class AddItemFragmentTest {

    @Test
    public void testValidate_ValidInput_ReturnsTrue() {
        // Arrange
        AddItemFragment addItemFragment = new AddItemFragment();
        String name = "Test Item";
        int month = 6;
        int day = 15;
        int year = 2023;
        String description = "This is a test item.";
        String make = "Test Make";
        String model = "Test Model";
        int serialNumber = 12345;
        double value = 100.0;
        String detail = "Additional details";

        // Act
        boolean result = addItemFragment.validate(name, month, day, year, description, make, model, serialNumber, value, detail);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testValidate_InvalidName_ReturnsFalse() {
        // Arrange
        AddItemFragment addItemFragment = new AddItemFragment();
        String name = "";  // Empty name

        // Act
        boolean result = addItemFragment.validate(name, 6, 15, 2023, "Test Description", "Test Make", "Test Model", 12345, 100.0, "Additional details");

        // Assert
        assertFalse(result);
    }

    @Test
    public void testValidate_InvalidMonth_ReturnsFalse() {
        // Arrange
        AddItemFragment addItemFragment = new AddItemFragment();
        int month = 13;  // Invalid month

        // Act
        boolean result = addItemFragment.validate("Test Item", month, 15, 2023, "Test Description", "Test Make", "Test Model", 12345, 100.0, "Additional details");

        // Assert
        assertFalse(result);
    }

    @Test
    public void testValidate_InvalidDay_ReturnsFalse() {
        // Arrange
        AddItemFragment addItemFragment = new AddItemFragment();
        int day = 32;  // Invalid day

        // Act
        boolean result = addItemFragment.validate("Test Item", 6, day, 2023, "Test Description", "Test Make", "Test Model", 12345, 100.0, "Additional details");

        // Assert
        assertFalse(result);
    }

    @Test
    public void testValidate_InvalidYear_ReturnsFalse() {
        // Arrange
        AddItemFragment addItemFragment = new AddItemFragment();
        int year = 2036;  // Invalid year

        // Act
        boolean result = addItemFragment.validate("Test Item", 6, 15, year, "Test Description", "Test Make", "Test Model", 12345, 100.0, "Additional details");

        // Assert
        assertFalse(result);
    }

    @Test
    public void testValidate_InvalidMake_ReturnsFalse() {
        // Arrange
        AddItemFragment addItemFragment = new AddItemFragment();
        String make = "";  // Empty make

        // Act
        boolean result = addItemFragment.validate("Test Item", 6, 15, 2023, "Test Description", make, "Test Model", 12345, 100.0, "Additional details");

        // Assert
        assertFalse(result);
    }

    @Test
    public void testValidate_InvalidModel_ReturnsFalse() {
        // Arrange
        AddItemFragment addItemFragment = new AddItemFragment();
        String model = null;  // Null model

        // Act
        boolean result = addItemFragment.validate("Test Item", 6, 15, 2023, "Test Description", "Test Make", model, 12345, 100.0, "Additional details");

        // Assert
        assertFalse(result);
    }

    @Test
    public void testValidate_InvalidValue_ReturnsFalse() {
        // Arrange
        AddItemFragment addItemFragment = new AddItemFragment();
        double value = -50.0;  // Negative value

        // Act
        boolean result = addItemFragment.validate("Test Item", 6, 15, 2023, "Test Description", "Test Make", "Test Model", 12345, value, "Additional details");

        // Assert
        assertFalse(result);
    }

    // Add more test cases for other validation rules...

}
