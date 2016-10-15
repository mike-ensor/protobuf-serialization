package com.dev9.ensor.model;

public class Item {

    private String name;
    private String description;
    private BaseQuantity baseQuantity;

    public Item(String name, String description, BaseQuantity baseQuantity) {
        this.name = name;
        this.description = description;
        this.baseQuantity = baseQuantity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BaseQuantity getBaseQuantity() {
        return baseQuantity;
    }
}
