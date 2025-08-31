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
    @Column()
    private String name;
    @Column(nullable = false)
    private String model;
    @Column(name = "serial_number")
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "equipment_status")
    private EquipmentStatus equipmentStatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contragent_id")
    private Contragent contragent;




}
