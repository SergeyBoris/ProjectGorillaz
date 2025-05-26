package com.javarush.borisov.db.Dto;

import com.javarush.borisov.db.constants.EquipmentStatus;
import com.javarush.borisov.entity.Contragent;
import com.javarush.borisov.entity.Equipment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter@Setter
public class EquipmentDto {

    private String model;
    private String serialNumber;
    private EquipmentStatus equipmentStatus;
    private Contragent contragent;

    public EquipmentDto(Equipment equipment) {
        this.model = equipment.getModel();
        this.serialNumber = equipment.getSerialNumber();
        this.equipmentStatus = equipment.getEquipmentStatus();
        this.contragent = equipment.getContragent();
    }
}
