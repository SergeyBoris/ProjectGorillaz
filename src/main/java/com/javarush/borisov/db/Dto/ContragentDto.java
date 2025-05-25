package com.javarush.borisov.db.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.javarush.borisov.entity.Contragent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContragentDto {

    private String name;

    public ContragentDto(Contragent contragent) {
        this.name = contragent.getName();
    }
    @JsonValue
    public String getName() {
        return name;
    }
}
