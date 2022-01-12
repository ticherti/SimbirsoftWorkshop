package com.github.ticherti.simplechat.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMINISTRATOR(Set.of( Permission.RENAME_USER, Permission.MAKE_MODERATOR,
            Permission.BAN_USER, Permission.DELETE_MESSAGE,
            Permission.SEND_MESSAGE, Permission.CREATE_ROOM,
            Permission.CONNECT_USER, Permission.DISCONNECT_USER,
            Permission.RENAME_ROOM, Permission.DELETE_ROOM)),

    MODERATOR(Set.of(Permission.BAN_USER, Permission.DELETE_MESSAGE,
            Permission.SEND_MESSAGE, Permission.CREATE_ROOM,
            Permission.CONNECT_USER, Permission.DISCONNECT_USER,
            Permission.RENAME_ROOM, Permission.DELETE_ROOM)),

    USER(Set.of(Permission.SEND_MESSAGE, Permission.CREATE_ROOM,
            Permission.CONNECT_USER, Permission.DISCONNECT_USER,
            Permission.RENAME_ROOM, Permission.DELETE_ROOM));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
