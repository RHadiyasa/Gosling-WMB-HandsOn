package com.enigma.wmb_api.constant;

import lombok.Getter;

@Getter
public enum UserRole {
    ROLE_CUSTOMER("PELANGGAN"),
    ROLE_STAFF("KARYAWAN");

    private final String value;

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
