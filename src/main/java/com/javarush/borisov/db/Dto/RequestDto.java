package com.javarush.borisov.db.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javarush.borisov.db.constants.RequestStatus;
import com.javarush.borisov.entity.Request;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter@Setter
public class RequestDto {

    private Long id;
    private String reqNumber;
    private String customer;
    private String customerPhone;
    private String address;
    private Set<EquipmentDto> equipmentsMontage;
    private Set<EquipmentDto> equipmentsUnmontage;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime sla;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDateTime closeDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime lastUpdate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime createDate;
    private String comment;
    private String linkToAktFile;
    private String status;
    private String contragent;
    private String user;

    public RequestDto(Request request) {
        this.id = request.getId();
        this.reqNumber = request.getReqNumber() != null ? request.getReqNumber() : "";
        this.customer = request.getCustomer()!=null ? request.getCustomer() : "";
        this.customerPhone = request.getCustomerPhone()!=null ? request.getCustomerPhone() : "";
        this.address = request.getAddress()!=null ? request.getAddress() : "";
        this.equipmentsMontage = request.getEquipmentsMontage() != null
                ? request.getEquipmentsMontage().stream()
                .map(EquipmentDto::new)
                .collect(Collectors.toSet())
                : Collections.emptySet();

        this.equipmentsUnmontage = request.getEquipmentsUnmontage() != null
                ? request.getEquipmentsUnmontage().stream()
                .map(EquipmentDto::new)
                .collect(Collectors.toSet())
                : Collections.emptySet();

        this.sla = request.getSla()!=null ? request.getSla() : LocalDateTime.now();
        this.createDate = request.getCreateDate();
        this.closeDate = request.getCloseDate()!=null ? request.getCloseDate() : null;
        this.comment = comment != null ? comment : "";

        this.status = request.getStatus().getName();
        this.contragent = request.getContragent()!=null ? request.getContragent().getName() : null;
        this.user = request.getUser().getName();
    }

    @Override
    public String toString() {
        return "Request{" +
               "reqNumber='" + reqNumber + '\'' +
               ", address='" + address + '\'' +
               ", equipmentsMontage=" + equipmentsMontage +
               ", equipmentsUnmontage=" + equipmentsUnmontage +
               ", sla=" + sla +
               ", closeDate=" + closeDate +
               ", status=" + status +
               '}';
    }
}

