package com.cardetails.model;

public enum Role {
    ADMIN,
    USER;

    public static Role from(String value) {
        if (value == null) {
            return USER;
        }
        if (ADMIN.name().equalsIgnoreCase(value)) {
            return ADMIN;
        }
        return USER;
    }
}
