package com.collaborate.FitnessApp.repository;

import com.collaborate.FitnessApp.domain.entities.ClientDetails;
import com.collaborate.FitnessApp.domain.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientDetailsRepository extends JpaRepository<ClientDetails, Long> {

     boolean existsByClient_Id(Long clientId);

    Optional<ClientDetails> findByClient_Id(Long clientId);

    @Modifying
    @Query("UPDATE ClientDetails c SET c.status = :status WHERE c.id = :id")
    boolean updateStatusById(@Param("status") String status, @Param("id") Long id);

    @Modifying
    @Query("UPDATE ClientDetails c SET c.status = :status WHERE c.client = :client")
    boolean updateStatusByClient_Id(@Param("status") String status, @Param("client") Long clientId);

    List<ClientDetails> findByStatus(String status);

    Page<ClientDetails> findByStatusIn(List<Status> statuses, Pageable pageable);
}
