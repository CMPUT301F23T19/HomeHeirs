package com.example.homeheirs;

import android.net.Uri;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Represents an item in the item list.
 * Each item has a name, purchase date, description, make, model, serial number, estimated value, additional comments, and an optional list of tags.
 * Implements Serializable to support serialization, and can be used for data storage or communication between activities.
 * @author Archi Patel
 */


public class Item implements Serializable {
    private String name;
    private int purchase_month;
    private int purchase_day;
    private int purchase_year;
    private String description;
    private String make;
    private String model;
    private int serial_number;
    private double estimated_value;
    private String comment;

    private boolean isSelected = false;

    private String Date_identifier;

    private List<String> image_uriList;

    public String getDate_identifier() {
        return Date_identifier;
    }

    public void setDate_identifier(String date_identifier) {
        Date_identifier = date_identifier;
    }

    public List<String> getImage_uriList() {
        return image_uriList;
    }

    public void setImage_uriList(List<String> image_uriList) {
        this.image_uriList = image_uriList;
    }

    public void add_uri(String image_date) {
        image_uriList.add(image_date);
    }

    public void deleteImageUri(String imageUri) {
         image_uriList.remove(imageUri);
    }

    /**
     * Gets the list of tags associated with the item.
     *
     * @return The list of tags.
     */
    public List<Tag> getTag_list() {
        return tag_list;
    }

    /**
     * Adds a new tag to the item's tag list.
     *
     * @param new_tag The tag to be added.
     */
    public void add_tag(Tag new_tag) {
        tag_list.add(new_tag);
    }


    public void remove_tag(Tag tag){
        tag_list.remove(tag);
    }

    /**
     * List of tags associated with the item.
     */
    private List<Tag> tag_list;

    /**
     * Default constructor required for Firebase.
     */
    public Item(){
        //required for firebase
    }



    /**
     * Constructs an Item with the specified details.
     *
     * @param name            The name of the item.
     * @param purchase_month  The month of purchase.
     * @param purchase_day    The day of purchase.
     * @param purchase_year   The year of purchase.
     * @param description     The description of the item.
     * @param make            The make of the item.
     * @param model           The model of the item.
     * @param serial_number   The serial number of the item.
     * @param estimated_value The estimated value of the item.
     * @param comment         Additional comments about the item.
     */
    public Item(String name, int purchase_month, int purchase_day, int purchase_year, String description, String make, String model, int serial_number, double estimated_value, String comment) {
        this.name = name;
        this.purchase_month = purchase_month;
        this.purchase_day = purchase_day;
        this.purchase_year = purchase_year;
        this.description = description;
        this.make = make;
        this.model = model;
        this.serial_number = serial_number;
        this.estimated_value = estimated_value;
        this.comment = comment;
        //initialize our list on creation in case tags need to be added
        this.tag_list = new ArrayList<>();
        this.image_uriList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date current = new Date();
        this.Date_identifier = format.format(current);
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPurchase_month() {
        return purchase_month;
    }

    public void setPurchase_month(int purchase_month) {
        this.purchase_month = purchase_month;
    }

    public int getPurchase_day() {
        return purchase_day;
    }

    public void setPurchase_day(int purchase_day) {
        this.purchase_day = purchase_day;
    }

    public int getPurchase_year() {
        return purchase_year;
    }

    public void setPurchase_year(int purchase_year) {
        this.purchase_year = purchase_year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(int serial_number) {
        this.serial_number = serial_number;
    }

    public double getEstimated_value() {
        return estimated_value;
    }

    public void setEstimated_value(double estimated_value) {
        this.estimated_value = estimated_value;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Checks if the item is selected.
     *
     * @return True if the item is selected, false otherwise.
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * Sets the selection status of the item.
     *
     * @param selected True to mark the item as selected, false otherwise.
     */
    public void setSelected(boolean selected) {isSelected = selected;}

    public static Date createCustomDate(int day, int month, int year){
        String dateString = String.format("%04d/%02d/%02d", year, month, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle the exception appropriately in your code
        }
    }


    public static Comparator<Item> dateAscending = new Comparator<Item>() {
        @Override
        public int compare(Item item1, Item item2) {
            Date customDate1 = createCustomDate(item1.getPurchase_day(), item1.getPurchase_month(), item1.getPurchase_year());
            Date customDate2 = createCustomDate(item2.getPurchase_day(), item2.getPurchase_month(), item2.getPurchase_year());

            return customDate1.compareTo(customDate2);
        }
    };

    public static Comparator<Item> descriptionAscending = new Comparator<Item>()
    {
        @Override
        public int compare(Item item1, Item item2)
        {
            String description1 = item1.getDescription();
            String description2 = item2.getDescription();

            description1 = description1.toLowerCase();
            description2 = description2.toLowerCase();

            return description1.compareTo(description2);
        }
    };

    public static Comparator<Item> makeAscending = new Comparator<Item>()
    {
        @Override
        public int compare(Item item1, Item item2)
        {
            String make1 = item1.getMake();
            String make2 = item2.getMake();

            make1 = make1.toLowerCase();
            make2 = make2.toLowerCase();

            return make1.compareTo(make2);
        }
    };

    public static Comparator<Item> valueAscending = new Comparator<Item>()
    {
        @Override
        public int compare(Item item1, Item item2)
        {
            Double value1 = item1.getEstimated_value();
            Double value2 = item2.getEstimated_value();

            return Double.compare(value1, value2);
        }
    };

    public static Comparator<Item> tagAscending = new Comparator<Item>()
    {
        @Override
        public int compare(Item item1, Item item2)
        {
            List<Tag> tags1 = item1.getTag_list();
            List<Tag> tags2 = item2.getTag_list();

            String tag1 = tags1.get(0).getTag_name();
            String tag2 = tags2.get(0).getTag_name();

            // Compare the first tags
            int result = tag1.compareToIgnoreCase(tag2);

            // If the first tags are equal, compare the next tags
            if (result == 0 && tags1.size() > 1 && tags2.size() > 1) {
                tag1 = tags1.get(1).getTag_name();
                tag2 = tags2.get(1).getTag_name();
                result = tag1.compareToIgnoreCase(tag2);
            }

            return result;
        }
    };

}
