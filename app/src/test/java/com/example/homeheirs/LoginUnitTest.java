package com.example.homeheirs;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginUnitTest {

    private login loginActivity;

    @Before
    public void setUp() {
        loginActivity = new login();
        // Normally, you would set up mocks here
    }

    @Test
    public void validate_BothFieldsEmpty_ReturnsFalse() {
        assertFalse(loginActivity.validate("", ""));
    }

    @Test
    public void validate_UsernameEmpty_ReturnsFalse() {
        assertFalse(loginActivity.validate("", "password"));
    }

    @Test
    public void validate_PasswordEmpty_ReturnsFalse() {
        assertFalse(loginActivity.validate("username", ""));
    }

    @Test
    public void validate_ValidCredentials_ReturnsTrue() {
        assertTrue(loginActivity.validate("username", "password"));
    }


    @Test
    public void validate_InvalidEmailFormat_ReturnsFalse() {
        assertFalse(loginActivity.validate("username", "password"));
    }

    @Test
    public void validate_PasswordTooShort_ReturnsFalse() {
        assertFalse(loginActivity.validate("username@example.com", "pwd"));
    }



}
