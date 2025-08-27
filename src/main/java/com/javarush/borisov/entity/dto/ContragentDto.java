package com.javarush.borisov.entity.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import com.javarush.borisov.entity.Contragent;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContragentDto {

    private Long id;
    private String name;


}
