package com.enigma.wmb_api.constant;

public enum UserRole {
    CUSTOMER("Customer"),
    STAFF("Staff");


    private String value;

    UserRole(String value) {
        this.value = value;
    }

    public static UserRole fromValue(String value) {
        for (UserRole userRole : values()) {
            if (userRole.value.equalsIgnoreCase(value)) {
                return userRole;
            }
        }
        return null;
    }
}
