package com.javarush.borisov.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum RequestStatus {

    ASSIGNED,
    IN_PROGRESS,
    COMPLETED,
    FAIL_DEPARTURE,
    CANCELED;

  }
