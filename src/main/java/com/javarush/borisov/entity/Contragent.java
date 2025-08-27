package com.javarush.borisov.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contragents")
public class Contragent {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contragent_id")
    private Long id;

    @Column(name = "contragent_name")
    private String name;


    @Override
    public String toString() {
        return "Contragent{" +
               "name='" + name + '}';
    }
}
