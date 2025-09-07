package com.javarush.borisov.db.constants;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;



@Getter
public enum EquipmentStatus {
    WAREHOUSE("Склад"),
    ON_REQUEST("Установлено"),
    DEPARTED("Отправлено");
    @JsonValue
    private final String name;

    EquipmentStatus(String name) {
        this.name = name;
    }


}
