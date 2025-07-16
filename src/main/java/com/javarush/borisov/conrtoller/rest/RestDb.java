package com.javarush.borisov.conrtoller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javarush.borisov.entity.dto.old.RequestDtoOld;
import com.javarush.borisov.entity.dto.old.UserDtoOld;
import com.javarush.borisov.db.Service.RequestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/rest/db")
public class RestDb extends HttpServlet {

    RequestService requestService = new RequestService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Получаем id из query-параметра
        String idParam = req.getParameter("id");
        Long id = Long.parseLong(idParam);

        // Чтение JSON из тела запроса
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }


        String json = sb.toString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        RequestDtoOld requestDtoOld = mapper.readValue(json, RequestDtoOld.class);
        RequestService requestService = new RequestService();
        requestDtoOld.setId(id);

        if(requestService.closeRequest(requestDtoOld)){
            resp.setContentType("application/json");
            resp.getWriter().write("{\"success\": true}");
        }else {
            resp.setContentType("application/json");
            resp.getWriter().write("{\"success\": false}");
        };


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        if (req.getParameter("requestsToShow") != null) {

            UserDtoOld userDtoOld = (UserDtoOld) session.getAttribute("user");
            List<RequestDtoOld> requestDtoOlds = requestService.getAssignedRequestDtos(userDtoOld);
            sendResponse(resp, requestDtoOlds);
            return;

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
