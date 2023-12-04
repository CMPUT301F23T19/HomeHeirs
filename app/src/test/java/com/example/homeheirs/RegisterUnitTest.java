package com.example.homeheirs;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegisterUnitTest {

    private Register registerActivity;

    @Before
    public void setUp() {
        registerActivity = new Register();
        // Setup if needed
    }

    @Test
    public void validate_EmptyFields_ReturnsFalse() {
        assertFalse(registerActivity.validate("", "", ""));
    }

    @Test
    public void validate_UsernameEmpty_ReturnsFalse() {
        assertFalse(registerActivity.validate("", "password", "password"));
    }

    @Test
    public void validate_PasswordEmpty_ReturnsFalse() {
        assertFalse(registerActivity.validate("username@example.com", "", ""));
    }

    @Test
    public void validate_ConfirmPasswordEmpty_ReturnsFalse() {
        assertFalse(registerActivity.validate("username@example.com", "password", ""));
    }

    @Test
    public void validate_PasswordsDoNotMatch_ReturnsFalse() {
        assertFalse(registerActivity.validate("username@example.com", "password", "different"));
    }

    @Test
    public void validate_ValidInputs_ReturnsTrue() {
        assertTrue(registerActivity.validate("username@example.com", "password", "password"));
    }


}
