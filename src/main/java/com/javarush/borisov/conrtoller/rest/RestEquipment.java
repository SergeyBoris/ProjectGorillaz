package com.javarush.borisov.conrtoller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.borisov.entity.dto.old.EquipmentDtoOld;
import com.javarush.borisov.db.Service.EquipmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/rest-equipment")
public class RestEquipment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String equipment = req.getParameter("equipment");
        String contragent = req.getParameter("contragent");
        EquipmentService equipmentService = new EquipmentService();
        if (contragent == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }else {

            List<EquipmentDtoOld> bySerialAndContragent = equipmentService.getBySerialAndContragent(equipment, contragent,20);
            resp.setContentType("application/json");
            new ObjectMapper().writeValue(resp.getWriter(),bySerialAndContragent);
        }
    }
}
