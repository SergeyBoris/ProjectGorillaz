package com.javarush.khmelov.lesson14.cmd;

import com.javarush.khmelov.lesson14.config.Winter;
import com.javarush.khmelov.lesson14.entity.User;
import com.javarush.khmelov.lesson14.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Collection;

public class ListUser implements Command {

    private final UserService userService;

    public ListUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String  doGet(HttpServletRequest request) {
        Collection<User> users = userService.getAll();
        request.setAttribute("users", users);
        return getView();
    }


}