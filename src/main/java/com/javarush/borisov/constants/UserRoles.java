package com.javarush.borisov.constants;

import lombok.Getter;

import java.util.List;


@Getter
public enum UserRoles {
    ADMIN(List.of("", "/start-page", "/requests-list", "/login")),
    COORDINATOR(List.of("", "/start-page", "/requests-list", "/login")),
    ENGINEER(List.of("", "/start-page", "/requests-list", "/login")),
    GUEST(List.of("", "/start-page", "/login"));

    private final List<String> permissions;


    UserRoles(List<String> permissions) {

        this.permissions = permissions;
    }



}
