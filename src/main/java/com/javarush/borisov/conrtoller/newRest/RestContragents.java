package com.javarush.borisov.conrtoller.newRest;

import com.javarush.borisov.entity.Contragent;
import com.javarush.borisov.entity.dto.ContragentDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contragents")
public class RestContragents {

    @GetMapping("/all")
    public List<ContragentDto> getAllContragents() {
        ContragentDto contragentDto1 = new ContragentDto();
        contragentDto1.setId(1L);
        contragentDto1.setName("Страйк");
        ContragentDto contragentDto2 = new ContragentDto();
        contragentDto2.setId(2L);
        contragentDto2.setName("ПБФ");
        List<ContragentDto> contragentDtos = new ArrayList<ContragentDto>();
        contragentDtos.add(contragentDto1);
        contragentDtos.add(contragentDto2);
        return contragentDtos;
    }
}
