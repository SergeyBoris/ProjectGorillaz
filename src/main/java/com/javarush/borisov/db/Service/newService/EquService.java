package com.javarush.borisov.db.Service.newService;


import com.javarush.borisov.db.Repository.EquipmentRepo;
import com.javarush.borisov.entity.mapper.EquipmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
