package com.javarush.borisov.entity;

import com.javarush.borisov.db.constants.RequestStatus;
import com.javarush.borisov.entity.mapper.MapToJsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    @Column(name = "req_number")
    private String reqNumber;
    private String customer;
    @Column(name = "customer_phone")
    private String customerPhone;
    private String tid;
    @Column(name = "work-type")
    private String workType;
    @Column(name = "customer_address")
    private String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "requests_equipments_montage",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id"))
    private Set<Equipment> equipmentsMontage;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "requests_equipments_unmontage",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id"))

    private Set<Equipment> equipmentsUnmontage;


    private LocalDateTime sla;

    @Column(name = "closed_date")
    private LocalDateTime closeDate;

    @UpdateTimestamp
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    private String comment;

    @Column(name = "range_to_address")
    private int rangeToAddress;

    @Column(name = "link_to_akt_file")
    private String linkToAktFile;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
    private RequestStatus status;

    @ManyToOne
    @JoinColumn(name = "contragent_id")
    private Contragent contragent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "json")
    @Convert(converter = MapToJsonConverter.class)
    private Map<String, String> parameters;

    @Override
    public String toString() {
        return "Request{" +
               "reqNumber='" + reqNumber + '\'' +
               ", address='" + address + '\'' +
               '}';
    }
}
