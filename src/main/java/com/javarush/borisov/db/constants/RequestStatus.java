package com.javarush.borisov.db.constants;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import java.util.Arrays;


@Getter
public enum RequestStatus {

    ASSIGNED("Назначено"),
    IN_PROGRESS("Выехал"),
    COMPLETED("Выполнена"),
    FAIL_DEPARTURE("Ложный выезд"),
    CANCELED("Отменено"),
    CLOSED_BY_USER("Закрыто инженером");

    @JsonValue
    private final String name;
    RequestStatus(String name) {
        this.name = name;
    }

    public static RequestStatus engStatusByRuName(String name) {
        return Arrays.stream(values())
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Неизвестный статус: " + name));
    }





  }
