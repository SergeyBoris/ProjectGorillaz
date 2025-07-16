package com.javarush.borisov.conrtoller.newRest;

import com.javarush.borisov.entity.dto.RequestDto;
import com.javarush.borisov.entity.dto.old.RequestDtoOld;


import com.javarush.borisov.db.Service.newService.ReqService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(("/serialInfo"))
@AllArgsConstructor
public class RestSerial{
   private final ReqService reqService;

    @GetMapping
    public String getRequestWithSerials(@RequestParam("serial") String serial, Model model) {
        List<RequestDto> requests = reqService.getRequestWithSerial(serial);
        model.addAttribute("requests", requests);
        model.addAttribute("serial", serial);
        return "serial-info"; // будет искать templates/serial-info.html

    }


}
