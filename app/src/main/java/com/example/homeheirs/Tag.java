package com.example.homeheirs;


import android.app.Activity;

/**
 * This is a class that keeps track of all tags created
 * @author : Arsalan
 */
public class Tag extends Activity {
    //The class will descrive the tag object that each item will have-weak has a relationship
    //where in we will be able to
    private String tag_name;

    /**
     * An Empty Constructer that is required by Firebase
     */
    public Tag(){

    }


    /**
     * Constructor actually used to create the tag objects
     * @param : tag_name - String indicating the name of the tag
     */
    public Tag(String tag_name) {
        this.tag_name = tag_name;
    }


    /**
     * Method returns the name of the tag object
     * @return tag_name - String containing the tag_name
     */
    public String getTag_name() {
        return tag_name;
    }


    /**
     * Method that changes tag name
     * @param tag_name - String of the new name of the tag
     * @deprecated - Currently not in Use but may be used in future
     */
    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }
}
