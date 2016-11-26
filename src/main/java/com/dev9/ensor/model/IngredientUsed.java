package com.dev9.ensor.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IngredientUsed {

    private final MeasurementType type;
    private final Ingredient ingredient;
    private final Integer quantity;

    @JsonCreator
    public IngredientUsed(@JsonProperty("ingredient") Ingredient ingredient, @JsonProperty("type") MeasurementType type, @JsonProperty("quantity") Integer quantity) {
        this.ingredient = ingredient;
        this.type = type;
        this.quantity = quantity;
    }

    public MeasurementType getType() {
        return type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public String toString() {
        return "IngredientUsed{" +
                "type=" + type +
                ", ingredient=" + ingredient +
                ", quantity=" + quantity +
                '}';
    }
}
