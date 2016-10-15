package com.dev9.ensor.model;

public class IngredientUsed {

    private final MeasurementType type;
    private final Ingredient ingredient;
    private final Integer quantity;

    public IngredientUsed(Ingredient ingredient, MeasurementType type, Integer quantity) {
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
}
