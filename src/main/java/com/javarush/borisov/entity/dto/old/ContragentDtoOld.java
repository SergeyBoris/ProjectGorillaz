package com.javarush.borisov.entity.dto.old;

import com.fasterxml.jackson.annotation.JsonValue;
import com.javarush.borisov.entity.Contragent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContragentDtoOld {

    private String name;

    public ContragentDtoOld(Contragent contragent) {
        this.name = contragent.getName();
    }
    @JsonValue
    public String getName() {
        return name;
    }
}
