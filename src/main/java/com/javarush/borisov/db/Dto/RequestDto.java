package com.javarush.borisov.db.Dto;

import com.javarush.borisov.constants.RequestStatus;
import com.javarush.borisov.entity.Contragent;
import com.javarush.borisov.entity.Equipment;
import com.javarush.borisov.entity.Request;
import com.javarush.borisov.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class RequestDto {


    private String reqNumber;
    private String customer;
    private String customerPhone;
    private String address;
    private Set<EquipmentDto> equipmentsMontage;
    private Set<EquipmentDto> equipmentsUnmontage;
    private LocalDateTime sla;
    private LocalDateTime closeDate;
    private LocalDateTime lastUpdate;
    private LocalDateTime createDate;
    private String comment;
    private String linkToAktFile;
    private RequestStatus status;
    private Contragent contragent;
    private User user;

    public RequestDto(Request request) {
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
        this.comment = comment != null ? comment : "";

        this.status = request.getStatus();
        this.contragent = request.getContragent()!=null ? request.getContragent() : null;
        this.user = request.getUser();
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

