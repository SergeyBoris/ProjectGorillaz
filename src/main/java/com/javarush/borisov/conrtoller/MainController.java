package com.javarush.borisov.conrtoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping({"/", "/index"})
    public String index(Model model) {
      //  model.addAttribute("title", "Главная страница");
        return "index"; // Вернётся index.html или index.jsp, в зависимости от шаблонизатора
    }

    @GetMapping("/requests-list")
    public String requestList(Model model) {
        return "assigned-requests";
    }
}
