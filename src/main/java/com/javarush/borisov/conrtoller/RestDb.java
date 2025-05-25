package com.javarush.borisov.conrtoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javarush.borisov.db.Dto.RequestDto;
import com.javarush.borisov.db.Dto.UserDto;
import com.javarush.borisov.db.Service.RequestService;
import com.javarush.borisov.db.constants.UserRoles;
import com.javarush.borisov.entity.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/rest/db")
public class RestDb extends HttpServlet {

    RequestService requestService = new RequestService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        if (req.getParameter("requestsToShow") != null) {

            UserDto userDto = (UserDto) session.getAttribute("user");
            List<RequestDto> requestDtos = requestService.getAssignedRequestDtos(userDto);
            sendResponse(resp, requestDtos);
            return;

        }
        if (req.getParameter("closeReq") != null) {
            Long closeReqId = Long.parseLong(req.getParameter("closeReq"));
            Boolean success = requestService.closeRequest(closeReqId);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            if (success) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"success\":true}");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"success\":false}");
            }

        }


    }

    private static void sendResponse(HttpServletResponse resp, List<?> toShow) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        String jsonArray = "";
        StringBuilder jsonResp = new StringBuilder();
        jsonArray = mapper.writeValueAsString(toShow);
        jsonResp.append(jsonArray);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonArray);
    }





}
