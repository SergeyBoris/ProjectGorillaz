package com.javarush.borisov.conrtoller.newRest;

import com.javarush.borisov.db.Service.newService.ContragentService;
import com.javarush.borisov.entity.dto.ContragentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contragents")
@RequiredArgsConstructor
public class RestContragents {
    private final ContragentService contragentService;

    @GetMapping("/all")
    public List<ContragentDto> findAll() {

        return contragentService.findAll();
    }
}
