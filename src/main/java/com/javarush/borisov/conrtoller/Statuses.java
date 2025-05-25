package com.javarush.borisov.conrtoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.borisov.db.constants.RequestStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/statuses")
public class Statuses extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {

        List<String> statuses = RequestStatus.getNames();
        resp.setContentType("application/json");
        new ObjectMapper().writeValue(resp.getWriter(), statuses);
    }
}
