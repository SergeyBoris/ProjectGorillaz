package com.javarush.borisov.db.Service;

import com.javarush.borisov.db.Dao.EquipmentDao;
import com.javarush.borisov.db.Dto.EquipmentDto;

import java.util.List;

public class EquipmentService {
    public List<EquipmentDto> getBySerialAndContragent(String serial, String contragent, int limit){
            EquipmentDao equipmentDao = new EquipmentDao();

         return equipmentDao.getBySerialAndContragentLimit(serial,contragent, limit).stream().map(EquipmentDto::new).toList();
    }
}
