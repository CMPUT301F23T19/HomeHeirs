package com.example.homeheirs;
import org.junit.Test;
import static org.junit.Assert.*;

public class TagTest {

    @Test
    public void testGetTag_name() {
        Tag tag = new Tag("TestTag");
        assertEquals("TestTag", tag.getTag_name());
    }

    @Test
    public void testSetTag_name() {
        Tag tag = new Tag("OriginalTag");
        tag.setTag_name("NewTag");
        assertEquals("NewTag", tag.getTag_name());
    }
}



