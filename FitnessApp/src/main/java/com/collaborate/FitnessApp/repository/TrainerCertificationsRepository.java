package com.collaborate.FitnessApp.repository;

import com.collaborate.FitnessApp.domain.entities.TrainerCertifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerCertificationsRepository extends JpaRepository<TrainerCertifications, Long> {
}
