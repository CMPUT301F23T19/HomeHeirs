package com.example.homeheirs;

import org.junit.Test;
import static org.junit.Assert.*;

public class TagTest {

    @Test
    public void testGetTag_name() {
        // Arrange
        Tag tag = new Tag("TestTag");

        // Act
        String tagName = tag.getTag_name();

        // Assert
        assertEquals("TestTag", tagName);
    }

    @Test
    public void testSetTag_name() {
        // Arrange
        Tag tag = new Tag();

        // Act
        tag.setTag_name("NewTag");

        // Assert
        assertEquals("NewTag", tag.getTag_name());
    }

    @Test
    public void testEmptyConstructor() {
        // Arrange and Act
        Tag tag = new Tag();

        // Assert
        assertNull(tag.getTag_name());
    }

    @Test
    public void testParameterizedConstructor() {
        // Arrange
        Tag tag = new Tag("ConstructorTag");

        // Assert
        assertEquals("ConstructorTag", tag.getTag_name());
    }
}



