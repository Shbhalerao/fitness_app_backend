package com.collaborate.FitnessApp.repository;

import com.collaborate.FitnessApp.domain.entities.FitnessCenterDetails;
import com.collaborate.FitnessApp.domain.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FitnessCenterDetailsRepository extends JpaRepository<FitnessCenterDetails, Long> {
    Optional<FitnessCenterDetails> findByCenter_Id(Long centerId);
    boolean existsByCenter_Id(Long centerId);
    List<FitnessCenterDetails> findByStatus(Status status);
    Page<FitnessCenterDetails> findByStatusIn(List<Status> statuses, Pageable pageable);
}
