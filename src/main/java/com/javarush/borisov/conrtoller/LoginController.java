package com.javarush.borisov.conrtoller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html
    }

    @GetMapping("/")
    public String homePage() {
        return "index"; // index.html
    }
}