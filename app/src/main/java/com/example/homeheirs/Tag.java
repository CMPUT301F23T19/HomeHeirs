package com.example.homeheirs;

import java.io.Serializable;

public class Tag  {
    //The class will descrive the tag object that each item will have-weak has a relationship
    //where in we will be able to
    private String tag_name;

    public Tag(){

    }

    public Tag(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }
}
