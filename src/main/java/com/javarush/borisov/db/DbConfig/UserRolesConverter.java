package com.javarush.borisov.db.DbConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.borisov.db.constants.UserRoles;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter(autoApply = true)
public class UserRolesConverter implements AttributeConverter<UserRoles, String> {
    ObjectMapper mapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(UserRoles userRoles) {

            return userRoles.name();

    }

    @Override
    public UserRoles convertToEntityAttribute(String s) {

            return UserRoles.valueOf(s);

    }
}



