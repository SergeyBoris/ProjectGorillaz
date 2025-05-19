package com.javarush.borisov.db.Dto;

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
}
