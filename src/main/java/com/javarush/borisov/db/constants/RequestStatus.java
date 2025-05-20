package com.javarush.borisov.db.constants;

import lombok.Getter;

@Getter
public enum RequestStatus {

    ASSIGNED("Назначено"),
    IN_PROGRESS("Выехал"),
    COMPLETED("Выполнена"),
    FAIL_DEPARTURE("Ложный выезд"),
    CANCELED("Отменено");

    private final String name;
    RequestStatus(String name) {
        this.name = name;
    }

  }
