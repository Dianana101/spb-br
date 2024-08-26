package com.example.spb_br.constant;

public enum Currency {
    RUR("RUR"),
    USD("USD"),
    EUR("EUR");

    public final String label;

    private Currency(String label) {
        this.label = label;
    }
}
