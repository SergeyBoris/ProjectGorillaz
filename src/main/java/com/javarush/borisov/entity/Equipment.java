package com.javarush.borisov.entity;

import com.javarush.borisov.db.constants.EquipmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "equipments")
public class Equipment {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id")
    private Long id;
    @Column(nullable = false)
    private String model;
    @Column(name = "serial_number")
    private String serialNumber;

    @ManyToMany(mappedBy = "equipmentsMontage", fetch = FetchType.EAGER)
    private Set<Request> requestWhereMontageEquipment;

    @ManyToMany(mappedBy = "equipmentsUnmontage", fetch = FetchType.EAGER)
    private Set<Request> requestWhereUnmotageEquipment;
    @Column(name = "equipment_status")
    private EquipmentStatus equipmentStatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contragent_id")
    private Contragent contragent;

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
               ", equipmentStatus=" + equipmentStatus +
               '}';
    }
}
