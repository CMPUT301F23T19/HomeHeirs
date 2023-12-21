package com.example.homeheirs;

import android.content.Context;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddItemFragmentTest {

    private AddItemFragment addItemFragment;
    private AddItemFragment.OnFragmentInteractionListener listenerMock;

    @Before
    public void setUp() {
        addItemFragment = new AddItemFragment();
        listenerMock = new AddItemFragment.OnFragmentInteractionListener() {
            @Override
            public void onOKPressed(Item item) {
                // Mock implementation
            }

            @Override
            public void onTagOKPressed(Tag tag) {
                // Mock implementation
            }
        };
    }

    @Test
    public void testValidateValidInput() {
        EditText editTextMock = new EditText(addItemFragment.getContext());
        editTextMock.setText("ValidInput");
        assertTrue(addItemFragment.validate(editTextMock, editTextMock.getText().toString()));
    }

    @Test
    public void testValidateEmptyInput() {
        EditText editTextMock = new EditText(addItemFragment.getContext());
        editTextMock.setText("");
        assertFalse(addItemFragment.validate(editTextMock, editTextMock.getText().toString()));
    }



    @Test
    public void testOnAttachValidContext() {
        // Set up the fragment with a mock context that implements OnFragmentInteractionListener
        AddItemFragment fragment = new AddItemFragment();
        fragment.onAttach((Context) listenerMock);

        // Assert that the listener was set to the valid context
        assertEquals(listenerMock, fragment.listener);
    }

    @Test(expected = RuntimeException.class)
    public void testOnAttachInvalidContext() {
        // Set up the fragment with a mock context that does not implement OnFragmentInteractionListener
        AddItemFragment fragment = new AddItemFragment();
        fragment.onAttach(addItemFragment.getContext()); // This should throw a RuntimeException
    }
}
