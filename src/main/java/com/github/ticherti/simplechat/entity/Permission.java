package com.github.ticherti.simplechat.entity;

public enum Permission {
//    todo Decide what permissions do I need. Definitely more of them.
    USERS_READ("users:read"),
    USERS_WRITE("users:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}