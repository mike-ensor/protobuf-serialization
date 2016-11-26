package com.dev9.ensor.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import java.util.List;

public class Recipe {

    private String name;
    private String description;
    private List<IngredientUsed> ingredientsWithQuantity;

    public Recipe(String name, String description) {
        this(name, description, Lists.newArrayList());
    }

    @JsonCreator
    public Recipe(@JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("ingredientsWithQuantity") List<IngredientUsed> ingredientsWithQuantity) {
        this.name = name;
        this.description = description;
        this.ingredientsWithQuantity = Lists.newArrayList(ingredientsWithQuantity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<IngredientUsed> getIngredientsWithQuantity() {
        return ingredientsWithQuantity;
    }

    public void setIngredientsWithQuantity(List<IngredientUsed> ingredientsWithQuantity) {
        this.ingredientsWithQuantity = ingredientsWithQuantity;
    }

    public void addItems(List<IngredientUsed> itemQuantity) {
        if (itemQuantity != null && itemQuantity.size() > 0) {
            this.ingredientsWithQuantity.addAll(itemQuantity);
        }
    }
}
