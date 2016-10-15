package com.dev9.ensor.model;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class Recipe {

    private String name;
    private String description;
    private Map<Item, Integer> itemQuantity;

    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;
        itemQuantity = Maps.newHashMap();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<Item, Integer> getItemQuantity() {
        return itemQuantity;
    }

    public void addItems(Map<Item, Integer> itemQuantity) {
        this.itemQuantity.putAll(itemQuantity);
    }
}
