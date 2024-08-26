package com.example.spb_br.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PurchaseType {
    ONLINE_PURCHASE("online"),
    SHOP_PURCHASE("shop");

    public final String label;

    private PurchaseType(String label) {
        this.label = label;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(this.label);
    }

    @JsonValue
    public String getValue() {
        return label;
    }

    @JsonCreator
    public static PurchaseType fromValue(String text) {
        for (PurchaseType b : PurchaseType.values()) {
            if (String.valueOf(b.label).equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
