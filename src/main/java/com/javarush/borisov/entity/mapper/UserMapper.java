package com.javarush.borisov.entity.mapper;

import com.javarush.borisov.entity.User;
import com.javarush.borisov.entity.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto dto);
}