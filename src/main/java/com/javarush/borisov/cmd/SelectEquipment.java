package com.javarush.borisov.cmd;

import com.javarush.borisov.db.Dto.EquipmentDto;
import com.javarush.borisov.db.Service.EquipmentService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;


public class SelectEquipment implements Command {
    @Override
    public String doGet(HttpServletRequest req) {

        return getView();

    }


}
