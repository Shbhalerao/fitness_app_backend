package com.collaborate.FitnessApp.domain.entities;

import com.collaborate.FitnessApp.domain.base.AuditInfo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client_details")
public class ClientDetails extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "height")
    private String height;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "fitness_goal")
    private String fitnessGoal;

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
