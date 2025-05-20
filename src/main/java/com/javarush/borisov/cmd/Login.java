package com.javarush.borisov.cmd;

import com.javarush.borisov.config.ClassCreator;
import com.javarush.borisov.db.Db;
import com.javarush.borisov.db.Dto.UserDto;
import com.javarush.borisov.db.Service.UserService;
import com.javarush.borisov.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class Login implements Command {
    @Override
    public String doGet(HttpServletRequest req) {

        UserService userService = ClassCreator.get(UserService.class);
        for (UserDto userDto : userService.getAllUsersDto()) {
            if (userDto.getMail().equals(req.getParameter("email")) && userDto.getPassword().equals(req.getParameter("password"))) {
                HttpSession session = req.getSession(false);
                session.setAttribute("user", userDto);
                session.setMaxInactiveInterval(300 * 60);

               return "/start-page";
            }
        }

       return getView() ;
    }
}
