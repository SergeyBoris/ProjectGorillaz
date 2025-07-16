package com.javarush.borisov.cmd;

import jakarta.servlet.http.HttpServletRequest;


public class SelectEquipment implements Command {
    @Override
    public String doGet(HttpServletRequest req) {

        return getView();

    }


}
