package com.javarush.borisov.db.constants;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public enum RequestStatus {

    ASSIGNED("Назначено"),
    IN_PROGRESS("Выехал"),
    COMPLETED("Выполнена"),
    FAIL_DEPARTURE("Ложный выезд"),
    CANCELED("Отменено"),
    CLOSED_BY_USER("Закрыто инженером");

    private final String name;
    RequestStatus(String name) {
        this.name = name;
    }


    @JsonValue
    public String getName() {
        return name;
    }
    public static List<String> getNames() {
        return Arrays.stream(values())
                .map(RequestStatus::getName)
                .collect(Collectors.toList());
    }

  }
