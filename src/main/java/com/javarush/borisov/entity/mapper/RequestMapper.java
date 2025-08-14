package com.javarush.borisov.entity.mapper;

import com.javarush.borisov.entity.dto.RequestDto;
import com.javarush.borisov.entity.Request;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses ={EquipmentMapper.class, ContragentMapper.class, UserMapper.class})
public interface RequestMapper {


    RequestDto toDto(Request request);

    Request toEntity(RequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "equipmentsMontage", ignore = true)
    @Mapping(target = "equipmentsUnmontage", ignore = true)
    void updateEntityFromDto(RequestDto dto, @MappingTarget Request entity);
}