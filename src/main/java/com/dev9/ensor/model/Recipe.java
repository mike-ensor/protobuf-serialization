package com.dev9.ensor.model;

import com.google.common.collect.Lists;

import java.util.List;

public class Recipe {

    private String name;
    private String description;
    private List<IngredientUsed> ingredientsWithQuantity;

    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;
        ingredientsWithQuantity = Lists.newArrayList();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<IngredientUsed> getIngredientsWithQuantity() {
        return ingredientsWithQuantity;
    }

    public void addItems(List<IngredientUsed> itemQuantity) {
        this.ingredientsWithQuantity.addAll(itemQuantity);
    }
}
