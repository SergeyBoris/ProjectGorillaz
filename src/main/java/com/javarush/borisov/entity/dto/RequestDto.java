package com.javarush.borisov.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javarush.borisov.db.constants.RequestStatus;
import com.javarush.borisov.entity.Equipment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private Long id;
    private String reqNumber;
    private String customer;
    private String customerPhone;
    private String tid;
    private String workType;
    private String address;

    private Set<EquipmentDto> equipmentsMontage;
    private Set<EquipmentDto> equipmentsUnmontage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime sla;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime closeDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime lastUpdate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime createDate;

    private String comment;
    private int rangeToAddress;
    private String linkToAktFile;

    private RequestStatus status;

    private ContragentDto contragent;

    private UserDto user;

    private Map<String,String> parameters;
}
