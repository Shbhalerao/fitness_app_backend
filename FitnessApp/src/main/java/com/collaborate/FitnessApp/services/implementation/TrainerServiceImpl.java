package com.collaborate.FitnessApp.services.implementation;

import com.collaborate.FitnessApp.domain.dto.requests.TrainerRequest;
import com.collaborate.FitnessApp.domain.dto.responses.TrainerResponse;
import com.collaborate.FitnessApp.domain.entities.FitnessCenter;
import com.collaborate.FitnessApp.domain.entities.Trainer;
import com.collaborate.FitnessApp.domain.enums.Role;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.collaborate.FitnessApp.domain.base.UserContext;
import com.collaborate.FitnessApp.exceptions.BadRequestException;
import com.collaborate.FitnessApp.exceptions.DuplicateResourceException;
import com.collaborate.FitnessApp.exceptions.ResourceNotFoundException;
import com.collaborate.FitnessApp.mappers.TrainerMapper;
import com.collaborate.FitnessApp.repository.FitnessCenterRepository;
import com.collaborate.FitnessApp.repository.TrainerRepository;
import com.collaborate.FitnessApp.services.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);
    private final TrainerRepository trainerRepository;
    private final FitnessCenterRepository fitnessCenterRepository;

    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository, FitnessCenterRepository fitnessCenterRepository) {
        this.trainerRepository = trainerRepository;
        this.fitnessCenterRepository = fitnessCenterRepository;
    }

    @Override
    public TrainerResponse register(TrainerRequest trainerRequest) {
        logger.info("Registering new trainer with email: {}", trainerRequest.getEmailId());
        if (trainerRepository.existsByEmailId(trainerRequest.getEmailId())) {
            logger.error("Duplicate email: {}", trainerRequest.getEmailId());
            throw new DuplicateResourceException("Trainer already exists with email: " + trainerRequest.getEmailId());
        }
        if (trainerRepository.existsByContactNo(trainerRequest.getContactNo())) {
            logger.error("Duplicate contact: {}", trainerRequest.getContactNo());
            throw new DuplicateResourceException("Trainer already exists with contact: " + trainerRequest.getContactNo());
        }


        Trainer trainer = TrainerMapper.toEntity(trainerRequest);
        if(trainerRequest.getCenterId() != null) {
            FitnessCenter center = fitnessCenterRepository.findById(trainerRequest.getCenterId())
                    .orElseThrow(() -> new BadRequestException("Invalid Fitness Center Id: " + trainerRequest.getCenterId()));
            trainer.setCenter(center);
        }
        trainer.setCreatedBy(UserContext.getAuditField());
        trainer.setUpdatedBy(UserContext.getAuditField());
        trainer.setRole(Role.ROLE_TRAINER);
        trainer.setStatus(Status.ACTIVE);
        Trainer saved = trainerRepository.save(trainer);
        logger.info("Trainer registered with id: {}", saved.getId());
        return TrainerMapper.toResponse(saved);
    }

    @Override
    public TrainerResponse getById(Long id) {
        logger.info("Fetching trainer by id: {}", id);
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found for id: " + id));
        return TrainerMapper.toResponse(trainer);
    }

    @Override
    public TrainerResponse getByEmailId(String emailId) {
        logger.info("Fetching trainer by email: {}", emailId);
        Trainer trainer = trainerRepository.findByEmailId(emailId)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found for email: " + emailId));
        return TrainerMapper.toResponse(trainer);
    }

    @Override
    public TrainerResponse getByContactNo(String contactNo) {
        logger.info("Fetching trainer by contact: {}", contactNo);
        Trainer trainer = trainerRepository.findByContactNo(contactNo)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found for contact: " + contactNo));
        return TrainerMapper.toResponse(trainer);
    }

    @Override
    public List<TrainerResponse> getByStatus(String status) {
        logger.info("Fetching trainers by status: {}", status);
        if (!Status.isExactStatus(status)) {
            logger.error("Invalid status: {}", status);
            throw new BadRequestException("Invalid status: " + status);
        }
        List<Trainer> trainers = trainerRepository.findByStatus(status);
        if (trainers.isEmpty()) {
            logger.warn("No trainers found for status: {}", status);
            throw new ResourceNotFoundException("No trainers found for status: " + status);
        }
        return trainers.stream().map(TrainerMapper::toResponse).toList();
    }

    @Override
    public TrainerResponse update(TrainerRequest trainerRequest) {
        logger.info("Updating trainer with id: {}", trainerRequest.getId());
        if (trainerRequest.getId() == null) {
            logger.error("Null id in update request");
            throw new BadRequestException("Please provide id to update");
        }

        Trainer existing = trainerRepository.findById(trainerRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No such trainer present for id: " + trainerRequest.getId()));

        // Update only the fields provided in the request
        if (trainerRequest.getFirstName() != null) {
            existing.setFirstName(trainerRequest.getFirstName());
        }
        if (trainerRequest.getLastName() != null) {
            existing.setLastName(trainerRequest.getLastName());
        }
        if (trainerRequest.getEmailId() != null) {
            if (!trainerRequest.getEmailId().equals(existing.getEmailId()) && trainerRepository.existsByEmailId(trainerRequest.getEmailId())) {
                throw new DuplicateResourceException("Trainer already exists with email: " + trainerRequest.getEmailId());
            }
            existing.setEmailId(trainerRequest.getEmailId());
        }
        if (trainerRequest.getContactNo() != null) {
            if (!trainerRequest.getContactNo().equals(existing.getContactNo()) && trainerRepository.existsByContactNo(trainerRequest.getContactNo())) {
                throw new DuplicateResourceException("Trainer already exists with contact: " + trainerRequest.getContactNo());
            }
            existing.setContactNo(trainerRequest.getContactNo());
        }
        if (trainerRequest.getCenterId() != null) {
            FitnessCenter center = fitnessCenterRepository.findById(trainerRequest.getCenterId())
                    .orElseThrow(() -> new BadRequestException("Invalid Fitness Center Id: " + trainerRequest.getCenterId()));
            existing.setCenter(center);
        }

        existing.setUpdatedBy("SELF");
        Trainer saved = trainerRepository.save(existing);
        logger.info("Trainer updated with id: {}", saved.getId());
        return TrainerMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting trainer with id: {}", id);
        if (!trainerRepository.existsById(id)) {
            logger.error("Trainer not found for id: {}", id);
            throw new ResourceNotFoundException("No record available for id: " + id);
        }
        trainerRepository.deleteById(id);
        logger.info("Trainer deleted with id: {}", id);
    }

    @Override
    public Page<TrainerResponse> getTrainers(List<Status> statuses, int page, int size) {
        logger.info("Fetching paged trainers, statuses: {}, page: {}, size: {}", statuses, page, size);
        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Trainer> trainerPage;
        if (statuses == null || statuses.isEmpty()) {
            trainerPage = trainerRepository.findAll(pageable);
        } else {
            for (Status s : statuses) {
                if (!Status.isExactStatus(s.name())) {
                    logger.error("Invalid status: {}", s);
                    throw new BadRequestException("Invalid status: " + s);
                }
            }
            trainerPage = trainerRepository.findAllByStatusIn(statuses, pageable);
            if (trainerPage.isEmpty()) {
                logger.warn("No trainers found for statuses: {}", statuses);
                throw new ResourceNotFoundException("No records found for status: " + statuses);
            }
        }
        if (trainerPage.isEmpty()) {
            logger.warn("No trainers found");
            throw new ResourceNotFoundException("No records found");
        }
        return trainerPage.map(TrainerMapper::toResponse);
    }

    @Override
    public Page<TrainerResponse> getByCenterId(Long centerId, int page, int size) {
        logger.info("Fetching trainers for centerId: {}, page: {}, size: {}", centerId, page, size);
        Page<Trainer> trainerPage = trainerRepository.findAllByCenter_Id(centerId, PageRequest.of(page, size, Sort.by("id").descending()));
        if (trainerPage.isEmpty()) {
            logger.warn("No trainers found for centerId: {}", centerId);
            throw new ResourceNotFoundException("No trainers found for centerId: " + centerId);
        }
        return trainerPage.map(TrainerMapper::toResponse);
    }
}
