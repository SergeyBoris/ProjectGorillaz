package com.javarush.borisov.db.Service.newService;

import com.javarush.borisov.entity.dto.RequestDto;
import com.javarush.borisov.entity.dto.old.RequestDtoOld;
import com.javarush.borisov.db.Repository.RequestRepo;
import com.javarush.borisov.entity.Request;
import com.javarush.borisov.entity.mapper.RequestMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReqService {

    private final RequestMapper requestMapper;
    private final RequestRepo requestRepo;

    public List<RequestDto> getRequestWithSerial(String serial) {
        List<Request> requests = requestRepo.findByEquipmentsMontage_SerialNumberOrEquipmentsUnmontage_SerialNumber(serial, serial);
        return requests.stream().map(requestMapper::toDto).toList();
    }





}
