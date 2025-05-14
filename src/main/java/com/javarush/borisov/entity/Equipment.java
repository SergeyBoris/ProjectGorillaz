package com.javarush.borisov.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "equipments")
public class Equipment {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id")
    Long id;
    @Column(nullable = false)
    String model;
    @Column(name = "serial_number")
    String serialNumber;

    @ManyToMany(mappedBy = "equipmentsMontage")
    List<Request> requestWhereMontageEquipment;

    @ManyToMany(mappedBy = "equipmentsUnmontage")
    List<Request> requestWhereUnmotageEquipment;

    public Equipment(String model, String serialNumber) {
        if (!(model == null) && !model.isEmpty()) {
            this.model = model;
        }else {this.model= "-";}
        if (!(serialNumber == null) && !serialNumber.isEmpty()) {
            this.serialNumber = serialNumber;
        }else {this.serialNumber= "-";}

    }

    @Override
    public String toString() {
        return "Equipment{" +
               "model='" + model + '\'' +
               ", serialNumber='" + serialNumber + '\'' +
               '}';
    }
}
