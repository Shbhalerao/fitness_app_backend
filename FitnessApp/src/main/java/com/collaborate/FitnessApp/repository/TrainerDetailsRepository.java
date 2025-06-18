package com.collaborate.FitnessApp.repository;

import com.collaborate.FitnessApp.domain.entities.TrainerDetails;
import com.collaborate.FitnessApp.domain.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerDetailsRepository extends JpaRepository<TrainerDetails, Long> {
    Page<TrainerDetails> findAllByStatusIn(List<Status> statuses, Pageable pageable);
}
