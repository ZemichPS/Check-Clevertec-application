package ru.clevertec.check.domain.model.valueobject;

public enum SaleConditionType {
    WHOLESALE,
    USUAL_PRICE;

    public static SaleConditionType fromString(String str) throws IllegalStateException {
        return switch (str){
            case "+", "true" -> WHOLESALE;
            case "-", "false" -> USUAL_PRICE;
            default -> throw new IllegalStateException("Unexpected value: " + str);
        };
    }

}
