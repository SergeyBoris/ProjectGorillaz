package com.javarush.borisov.db.Service;

import com.javarush.borisov.db.Dao.EquipmentDao;
import com.javarush.borisov.entity.dto.old.EquipmentDtoOld;

import java.util.List;

public class EquipmentService {
    public List<EquipmentDtoOld> getBySerialAndContragent(String serial, String contragent, int limit){
            EquipmentDao equipmentDao = new EquipmentDao();

         return equipmentDao.getBySerialAndContragentLimit(serial,contragent, limit).stream().map(EquipmentDtoOld::new).toList();
    }
}
