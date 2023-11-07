package com.example.homeheirs;

import java.util.ArrayList;
import java.util.List;

public class Item {
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

    public List<Tag> getTag_list() {
        return tag_list;
    }

    public void add_tag(Tag new_tag) {
        tag_list.add(new_tag);
    }

    //taglist is not necessary to define a class
    private List<Tag> tag_list;

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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {isSelected = selected;}

}