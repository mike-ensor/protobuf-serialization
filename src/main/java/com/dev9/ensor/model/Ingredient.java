package com.dev9.ensor.model;

public class Ingredient {

    private String name;
    private String description;

    public Ingredient(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
