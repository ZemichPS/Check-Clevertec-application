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

    public static SaleConditionType valueOf(boolean value) throws IllegalStateException {
        if(value) return WHOLESALE;
        return USUAL_PRICE;
    }

    public boolean toBool(){
        switch (this) {
            case WHOLESALE:
                return true;
            case USUAL_PRICE:
                return false;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

}
