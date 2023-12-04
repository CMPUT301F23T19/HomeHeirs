package com.example.homeheirs;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class loginTest {

    private login loginActivity;

    @Before
    public void setUp() {
        loginActivity = new login();
    }

    @Test
    public void testValidateWithEmptyUsernameAndPassword() {
        assertFalse(loginActivity.validate("", ""));
    }

    @Test
    public void testValidateWithEmptyUsername() {
        assertFalse(loginActivity.validate("", "password"));
    }

    @Test
    public void testValidateWithEmptyPassword() {
        assertFalse(loginActivity.validate("username@gmail.com", ""));
    }

    @Test
    public void testValidateWithNonEmptyCredentials() {
        assertTrue(loginActivity.validate("username@gmail.com", "password"));
    }

}