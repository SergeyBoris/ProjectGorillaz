package com.javarush.borisov.conrtoller.newRest;

import com.javarush.borisov.db.Service.newService.EquService;
import com.javarush.borisov.entity.dto.EquipmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipments")
@RequiredArgsConstructor
public class RestEquipments {

private final EquService equService;


    @GetMapping("/by-contragent/{contragentId}")
    public List<EquipmentDto> findByContragentId(@PathVariable("contragentId") Long contragentId) {
        return equService.findByContragentId(contragentId);
    }

    @GetMapping("/search-by-serial")
    public List<EquipmentDto> findBySerial(
            @RequestParam("serial") String serial,
            @RequestParam("contragentId") Long contragentId
    ) {
        return equService.findBySerialNumberContainingAndContragent(serial, contragentId);
    }

    @GetMapping("/search-names")
    public List<String> findAllNames(
            @RequestParam("name") String name

    ){
      return equService.findAllNames(name);
    }

    @GetMapping("/search-model")
    public List<String> findAllModels(
            @RequestParam("model") String model

    ){
        return equService.findAllModels(model);
    }

}
