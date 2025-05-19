package com.javarush.borisov.db.Dto;

import com.javarush.borisov.constants.EquipmentStatus;
import com.javarush.borisov.entity.Equipment;
import lombok.Getter;
import lombok.Setter;


@Getter@Setter
public class EquipmentDto {

    private String model;
    private String serialNumber;
    private EquipmentStatus equipmentStatus;

    public EquipmentDto(Equipment equipment) {
        this.model = equipment.getModel();
        this.serialNumber = equipment.getSerialNumber();
        this.equipmentStatus = equipment.getEquipmentStatus();
    }
}
