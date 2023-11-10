package com.example.homeheirs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an item in the item list.
 * Each item has a name, purchase date, description, make, model, serial number, estimated value, additional comments, and an optional list of tags.
 * Implements Serializable to support serialization, and can be used for data storage or communication between activities.
 * @author Archi Patel
 */


public class Item implements Serializable {
    private String name;
    private int purchase_month;
    private int purchase_year;
    private String description;
    private String make;
    private String model;
    private int serial_number;
    private double estimated_value;
    private String comment;

    private boolean isSelected = false;

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
     * @param purchase_year   The year of purchase.
     * @param description     The description of the item.
     * @param make            The make of the item.
     * @param model           The model of the item.
     * @param serial_number   The serial number of the item.
     * @param estimated_value The estimated value of the item.
     * @param comment         Additional comments about the item.
     */
    public Item(String name, int purchase_month, int purchase_year, String description, String make, String model, int serial_number, double estimated_value, String comment) {
        this.name = name;
        this.purchase_month = purchase_month;
        this.purchase_year = purchase_year;
        this.description = description;
        this.make = make;
        this.model = model;
        this.serial_number = serial_number;
        this.estimated_value = estimated_value;
        this.comment = comment;
        //initialize our list on creation in case tags need to be added
        this.tag_list = new ArrayList<>();
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

}
