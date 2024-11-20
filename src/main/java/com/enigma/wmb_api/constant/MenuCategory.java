package com.enigma.wmb_api.constant;

public enum MenuCategory {
    FOOD("Makanan"),
    BEVERAGE("Minuman");

    private String name;

    MenuCategory(String name) {
        this.name = name;
    }

    public static MenuCategory fromValue(String name) {
        for (MenuCategory value : values()) {
            if (value.name.equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
}
