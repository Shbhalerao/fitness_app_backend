package com.collaborate.FitnessApp.repository;

import com.collaborate.FitnessApp.domain.entities.FitnessCenter;
import com.collaborate.FitnessApp.domain.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FitnessCenterRepository extends JpaRepository<FitnessCenter, Long> {
    Optional<FitnessCenter> findByEmailId(String email);
    Optional<FitnessCenter> findByContactNo(String contactNo);
    boolean existsByEmailId(String emailId);
    boolean existsByContactNo(String contactNo);
    List<FitnessCenter> findByStatus(Status status);
    Page<FitnessCenter> findByStatusIn(List<Status> statuses, org.springframework.data.domain.Pageable pageable);
}
