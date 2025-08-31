package com.javarush.borisov.entity.dto;



import com.javarush.borisov.db.constants.EquipmentStatus;
import com.javarush.borisov.entity.Contragent;
import com.javarush.borisov.entity.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDto {

    private Long id;
    private String name;
    private String model;
    private String serialNumber;
    private EquipmentStatus equipmentStatus;

    private ContragentDto contragent;
}
