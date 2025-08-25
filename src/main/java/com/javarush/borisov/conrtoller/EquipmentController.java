package com.javarush.borisov.conrtoller;

import com.javarush.borisov.db.Service.newService.EquService;
import com.javarush.borisov.entity.dto.RequestDto;


import com.javarush.borisov.db.Service.newService.ReqService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/equipment")
@AllArgsConstructor
public class EquipmentController {
    private final ReqService reqService;
    private final EquService equService;

    @GetMapping("/in-request")
    public String getRequestWithSerials(@RequestParam("equipment") String serial, Model model) {
        List<RequestDto> requests = Optional.ofNullable(reqService.getRequestWithSerial(serial))
                .orElse(Collections.emptyList());
        model.addAttribute("requests", requests);

        return "info";

    }

    @GetMapping("/active-equipment")
    public String getActiveEquipment(Model model) {

        return "equip";
    }

    @DeleteMapping("/{reqId}/montage/{eqId}")
    public ResponseEntity<Void> deleteSerialMontageFromRequest
            (@PathVariable("reqId") Long reqId,
             @PathVariable("eqId") Long eqId) {
       if(equService.deleteEquipmentMontageFromRequest(reqId, eqId)) {
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{reqId}/unmontage/{eqId}")
    public ResponseEntity<Void> deleteSerialUnMontageFromRequest
            (@PathVariable("reqId") Long reqId,
             @PathVariable("eqId") Long eqId) {
        if(equService.deleteEquipmentUnmontageFromRequest(reqId, eqId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

}
