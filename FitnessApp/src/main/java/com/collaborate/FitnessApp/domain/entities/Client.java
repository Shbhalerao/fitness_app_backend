package com.collaborate.FitnessApp.domain.entities;


import com.collaborate.FitnessApp.domain.base.AuditInfo;
import com.collaborate.FitnessApp.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Data
@ToString
@Builder
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
public class Client extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email_id", nullable = false, unique = true)
    private String emailId;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="contact_no", nullable = false, unique = true)
    private String contactNo;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

}
