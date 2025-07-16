package com.javarush.borisov.conrtoller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.borisov.entity.dto.old.ContragentDtoOld;
import com.javarush.borisov.db.Service.ContragentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/get-contragents")
public class RestContragents extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       ContragentService contragentService = new ContragentService();
        List<ContragentDtoOld> contragents = contragentService.getAllContragents();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        new ObjectMapper().writeValue(resp.getWriter(), contragents);
    }
}
