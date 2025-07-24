package com.javarush.borisov.conrtoller.newRest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/temp")
public class RestDates {

    @GetMapping
    public String temp(){
        return "request-list";
    }

}
