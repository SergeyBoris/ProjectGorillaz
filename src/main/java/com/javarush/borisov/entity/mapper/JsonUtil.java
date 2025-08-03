package com.javarush.borisov.entity.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.borisov.entity.Equipment;
import com.javarush.borisov.entity.dto.EquipmentDto;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }
    public static Set<Equipment> fromJsonToSet(String json) {
        try {
            return mapper.readValue(json, new TypeReference<Set<Equipment>>() {});
        } catch (IOException e) {
            return new HashSet<>();
        }
    }
}