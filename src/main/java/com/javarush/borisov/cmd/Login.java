//package com.javarush.borisov.cmd;
//
//import com.javarush.borisov.config.ClassCreator;
//
//import com.javarush.borisov.entity.dto.old.UserDtoOld;
//import com.javarush.borisov.db.Service.newService.UserService;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//
//public class Login implements Command {
//    @Override
//    public String doGet(HttpServletRequest req) {
//
//        UserService userService = ClassCreator.get(UserService.class);
//        for (UserDtoOld userDtoOld : userService.getAllUsersDto()) {
//            if (userDtoOld.getMail().equals(req.getParameter("email")) && userDtoOld.getPassword().equals(req.getParameter("password"))) {
//                HttpSession session = req.getSession(false);
//                session.setAttribute("user", userDtoOld);
//                session.setMaxInactiveInterval(300 * 60);
//
//               return "/start-page";
//            }
//        }
//
//       return getView() ;
//    }
//}
