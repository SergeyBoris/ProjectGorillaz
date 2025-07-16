package com.javarush.borisov.entity.dto.old;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javarush.borisov.entity.Request;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter@Setter
public class RequestDtoOld {

    private Long id;
    private String reqNumber;
    private String customer;
    private String customerPhone;
    private String address;
    private Set<EquipmentDtoOld> equipmentsMontage;
    private Set<EquipmentDtoOld> equipmentsUnMontage;
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
    private String status;
    private String contragent;
    private String user;

    public RequestDtoOld(Request request) {
        this.id = request.getId();
        this.reqNumber = request.getReqNumber() != null ? request.getReqNumber() : "";
        this.customer = request.getCustomer()!=null ? request.getCustomer() : "";
        this.customerPhone = request.getCustomerPhone()!=null ? request.getCustomerPhone() : "";
        this.address = request.getAddress()!=null ? request.getAddress() : "";
        this.equipmentsMontage = request.getEquipmentsMontage() != null
                ? request.getEquipmentsMontage().stream()
                .map(EquipmentDtoOld::new)
                .collect(Collectors.toSet())
                : Collections.emptySet();

        this.equipmentsUnMontage = request.getEquipmentsUnmontage() != null
                ? request.getEquipmentsUnmontage().stream()
                .map(EquipmentDtoOld::new)
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
               ", equipmentsUnmontage=" + equipmentsUnMontage +
               ", sla=" + sla +
               ", closeDate=" + closeDate +
               ", status=" + status +
               '}';
    }
}

