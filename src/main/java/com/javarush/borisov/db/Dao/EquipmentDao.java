package com.javarush.borisov.db.Dao;

import com.javarush.borisov.entity.Equipment;

public class EquipmentDao extends AbstractDao<Equipment> {
    public EquipmentDao() {
        super(Equipment.class);
    }
}
