package com.javarush.borisov.db.Dto;

import com.javarush.borisov.db.constants.UserRoles;

import com.javarush.borisov.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UserDto {

    private Long id;
    private String name;
    private String mail;
    private String password;
    private UserRoles role;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.mail = user.getMail();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
}
