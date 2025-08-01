package com.javarush.borisov.conrtoller.newRest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/temp")
public class RestDates {

    @GetMapping
    public String temp(){
        return "requests-list";
    }

}
