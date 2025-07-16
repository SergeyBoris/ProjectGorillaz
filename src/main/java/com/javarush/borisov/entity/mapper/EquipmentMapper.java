package com.javarush.borisov.entity.mapper;

import com.javarush.borisov.entity.Equipment;
import com.javarush.borisov.entity.dto.EquipmentDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ContragentMapper.class})
public interface EquipmentMapper {


    EquipmentDto toDto(Equipment equipment);

    Equipment toEntity(EquipmentDto dto);
}