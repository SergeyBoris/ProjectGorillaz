package com.javarush.borisov.db.Service.newService;


import com.javarush.borisov.db.Repository.EquipmentRepo;
import com.javarush.borisov.entity.dto.EquipmentDto;
import com.javarush.borisov.entity.mapper.EquipmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquService {
    private final EquipmentMapper equipmentMapper;
    private final EquipmentRepo equipmentRepo;

    public boolean deleteEquipmentMontageFromRequest(Long reqID,Long eqId) {
        return equipmentRepo.deleteEquipmentMontageFromRequest(reqID, eqId) > 0;
    }
    public boolean deleteEquipmentUnmontageFromRequest(Long reqID,Long eqId) {
        return equipmentRepo.deleteEquipmentUnmontageFromRequest(reqID,eqId)>0;
    }
    public List<EquipmentDto> findByContragentId(Long contragentId){
     return equipmentRepo.findByContragentId(contragentId).stream()
             .map(equipmentMapper::toDto)
             .toList();
    }

    public List<EquipmentDto> findBySerialNumberContainingAndContragent(String serialNumber,Long contragentId){
        return equipmentRepo.findBySerialNumberContainingAndContragent_Id(serialNumber,contragentId)
                .stream()
                .map(equipmentMapper::toDto)
                .toList();
    }
}
