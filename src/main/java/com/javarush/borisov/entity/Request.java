package com.javarush.borisov.entity;

import com.javarush.borisov.constants.RequestStatus;
import jakarta.persistence.*;
import lombok.*;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
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
    @Column(name = "customer_address")
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "requests_equipments_montage",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id"))
    private Set<Equipment> equipmentsMontage;

    @ManyToMany(fetch = FetchType.EAGER)
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

    @Transient
    private Map<String, String> parameters;

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
