package com.dev9.ensor.model;

public class BaseQuantity {

    private MeasurementType type;
    private Integer amount;

    public static BaseQuantity SINGLE_ITEM = new BaseQuantity();

    public BaseQuantity(MeasurementType type, Integer amount) {
        this.type = type;
        this.amount = amount;
    }

    private BaseQuantity() {
        this.type = MeasurementType.ITEM;
        this.amount = 1;
    }

    public MeasurementType getType() {
        return type;
    }

    public Integer getAmount() {
        return amount;
    }
}
