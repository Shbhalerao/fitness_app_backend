package com.collaborate.FitnessApp.domain.entities;

import com.collaborate.FitnessApp.domain.base.AuditInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trainer_details")
public class TrainerDetails extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainerId;

    @Column(name = "working_since")
    private Date workingSince;

    @Column(name = "address_line")
    private String addressLine;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "pincode")
    private Long pinCode;

}
