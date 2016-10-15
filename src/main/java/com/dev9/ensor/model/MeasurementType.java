package com.dev9.ensor.model;

public enum MeasurementType {

    OUNCE("oz"), GALLON("gal"), GRAMS("g"), CUP("cup"), TABLESPOON("Tlbs"), TEASPOON("tps"), POUND("lbs"), ITEM("item");

    private final String name;

    MeasurementType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
