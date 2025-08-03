package com.javarush.borisov.entity.mapper;

import com.javarush.borisov.entity.dto.RequestDto;
import com.javarush.borisov.entity.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses ={EquipmentMapper.class, ContragentMapper.class, UserMapper.class})
public interface RequestMapper {

    RequestDto toDto(Request request);
    @Mapping(target = "parameters", ignore = true)
    Request toEntity(RequestDto dto);
}