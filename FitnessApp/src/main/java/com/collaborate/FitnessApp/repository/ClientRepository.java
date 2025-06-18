package com.collaborate.FitnessApp.repository;

import com.collaborate.FitnessApp.domain.entities.Client;
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
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmailId(String email);

    boolean existsByEmailId(String emailId);

    boolean existsByContactNo(String contactNo);

    Optional<Client> findByContactNo(String contactNo);

    List<Client> findByStatus(String status);

    @Modifying
    @Query("UPDATE Client c SET c.status = :status WHERE c.emailId = :emailId")
    boolean disableByEmailById(@Param("status") String status, @Param("emailId") String emailId);

    @Modifying
    @Query("UPDATE Client c SET c.status = :status WHERE c.contactNo = :contactNo")
    boolean disableByContactNo(@Param("status") String status, @Param("contactNo") String contactNo);

    Page<Client> findAllByStatusIn(List<Status> statuses, Pageable pageable);
}
