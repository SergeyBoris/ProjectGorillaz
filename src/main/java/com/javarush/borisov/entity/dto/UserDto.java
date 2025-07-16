package com.javarush.borisov.entity.dto;

import com.javarush.borisov.db.constants.UserRoles;
import com.javarush.borisov.entity.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String mail;
    private String password;
    private UserRoles role;


}
