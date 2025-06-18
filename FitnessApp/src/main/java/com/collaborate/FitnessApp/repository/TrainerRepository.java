package com.collaborate.FitnessApp.repository;

import com.collaborate.FitnessApp.domain.entities.Trainer;
import com.collaborate.FitnessApp.domain.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findByEmailId(String email);

    boolean existsByEmailId(String emailId);

    boolean existsByContactNo(String contactNo);

    Page<Trainer> findAllByStatusIn(List<Status> statuses, Pageable pageable);
    List<Trainer> findByStatus(String status);

    Optional<Trainer> findByContactNo(String contactNo);
}
