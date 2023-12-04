package com.example.homeheirs;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;

public class ItemTest {

    @Test
    public void testAddAndRemoveTag() {
        Item item = new Item("Computer", 1,2, 2023, "Lenovo", "5i", "367", 123, 500, "it sucks");

        Tag tag1 = new Tag("Tag1");
        Tag tag2 = new Tag("Tag2");

        item.add_tag(tag1);
        item.add_tag(tag2);

        List<Tag> tagList = item.getTag_list();

        assertEquals(2, tagList.size());
        assertTrue(tagList.contains(tag1));
        assertTrue(tagList.contains(tag2));

        item.remove_tag(tag1);

        assertEquals(1, tagList.size());
        assertFalse(tagList.contains(tag1));
        assertTrue(tagList.contains(tag2));
    }


    /**
     * Method that tests to make sure that each item has unique identifier
     * Items should take time to add, so we add a sleep for 2000milis
     * Source: https://stackoverflow.com/questions/15938538/how-can-i-make-a-junit-test-wait
     */
    @Test
    public void testUniqueIdentifier() throws InterruptedException {
        Item item = new Item("Computer", 1,2, 2023, "Lenovo", "5i", "367", 123, 500, "it sucks");


        Thread.sleep(2000);

        Item item2 = new Item("TestItem", 1,3, 2023, "TestDescription", "TestMake", "TestModel", 123, 100.0, "TestComment");

        assertNotEquals(item2.getDate_identifier(),item.getDate_identifier());
    }


    @Test
    public void testImageUriOperations() {
        Item item = new Item("TestItem", 1,1, 2023, "TestDescription", "TestMake", "TestModel", 123, 100.0, "TestComment");

        String imageUri1 = "uri1";
        String imageUri2 = "uri2";

        // Adding image URIs
        item.add_uri(imageUri1);
        item.add_uri(imageUri2);

        List<String> imageUriList = item.getImage_uriList();

        assertEquals(2, imageUriList.size());
        assertTrue(imageUriList.contains(imageUri1));
        assertTrue(imageUriList.contains(imageUri2));

        // Removing image URI
        item.deleteImageUri(imageUri1);

        assertEquals(1, imageUriList.size());
        assertFalse(imageUriList.contains(imageUri1));
        assertTrue(imageUriList.contains(imageUri2));
    }

    @Test
    public void testComparators() {
        Item item1 = new Item("Item1", 1,3, 2023, "Description1", "Make1", "Model1", 123, 100.0, "Comment1");
        Item item2 = new Item("Item2", 2,4, 2022, "Description2", "Make2", "Model2", 456, 50.0, "Comment2");

        // Test description comparator
        List<Item> itemList = new ArrayList<>();
        itemList.add(item2);
        itemList.add(item1);

        Tag tag1 = new Tag("Tag1");
        Tag tag2 = new Tag("Tag2");
        Tag tag3 = new Tag("Tag3");

        item1.add_tag(tag1);
        item1.add_tag(tag3);
        item2.add_tag(tag1);
        item2.add_tag(tag2);

        List<Tag> tagList1 = item1.getTag_list();
        List<Tag> tagList2 = item2.getTag_list();

        itemList.sort(Item.dateAscending);

        assertEquals(2022, itemList.get(0).getPurchase_year());
        assertEquals(2023, itemList.get(1).getPurchase_year());

        itemList.sort(Item.descriptionAscending);

        assertEquals("Description1", itemList.get(0).getDescription());
        assertEquals("Description2", itemList.get(1).getDescription());

        // Test make comparator
        itemList.sort(Item.makeAscending);

        assertEquals("Make1", itemList.get(0).getMake());
        assertEquals("Make2", itemList.get(1).getMake());

        // Test value comparator
        itemList.sort(Item.valueAscending);

        assertEquals(50.0, itemList.get(0).getEstimated_value(), 0.001);
        assertEquals(100.0, itemList.get(1).getEstimated_value(), 0.001);

        // Text tag comparator
        itemList.sort(Item.tagAscending);

        assertEquals(tag2, itemList.get(0).getTag_list().get(1));
        assertEquals(tag3, itemList.get(1).getTag_list().get(1));
    }

    @Test
    public void testCreateCustomDate() {
        // Given
        int day = 4;
        int month = 12;
        int year = 2023;

        // When
        Date result = Item.createCustomDate(day, month, year);

        assertNotNull(String.valueOf(result));

        // You can also check if the result matches the expected date string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String actualDateString = dateFormat.format(result);
        assertEquals("2023/12/04", actualDateString);
    }
}
