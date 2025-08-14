package com.javarush.borisov.entity.mapper;

import com.javarush.borisov.entity.User;
import com.javarush.borisov.entity.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto dto);
}