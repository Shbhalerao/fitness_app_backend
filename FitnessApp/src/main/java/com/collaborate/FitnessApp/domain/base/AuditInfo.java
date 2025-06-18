package com.collaborate.FitnessApp.domain.base;

import com.collaborate.FitnessApp.domain.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@MappedSuperclass
@Data
public class AuditInfo {

    @Column(name="created_by",updatable = false)
    private String createdBy;

    @Column(name = "created_date",updatable = false, nullable = false)
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    @UpdateTimestamp
    private Date updatedDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;


}
