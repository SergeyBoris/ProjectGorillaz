package com.javarush.borisov.db.Dto;

import com.javarush.borisov.constants.UserRoles;

import com.javarush.borisov.entity.User;
import jakarta.persistence.EnumType;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UserDto {

    private String name;
    private String mail;
    private String password;
    private UserRoles role;

    public UserDto(User user) {
        this.name = user.getName();
        this.mail = user.getMail();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
}
