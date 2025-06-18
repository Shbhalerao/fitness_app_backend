package com.collaborate.FitnessApp.domain.entities;

import com.collaborate.FitnessApp.domain.base.AuditInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainer_certifications")
public class TrainerCertifications extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainerId;

    @Column(name = "certification_name")
    private String certificationName;

    @Column(name = "certification_date")
    private Date certificationDate;
}
