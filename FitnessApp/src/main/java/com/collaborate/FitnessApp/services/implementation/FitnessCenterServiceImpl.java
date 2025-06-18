package com.collaborate.FitnessApp.services.implementation;

import com.collaborate.FitnessApp.domain.dto.requests.FitnessCenterRequest;
import com.collaborate.FitnessApp.domain.dto.responses.FitnessCenterResponse;
import com.collaborate.FitnessApp.domain.entities.FitnessCenter;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.collaborate.FitnessApp.exceptions.DuplicateResourceException;
import com.collaborate.FitnessApp.exceptions.ResourceNotFoundException;
import com.collaborate.FitnessApp.mappers.FitnessCenterMapper;
import com.collaborate.FitnessApp.repository.FitnessCenterDetailsRepository;
import com.collaborate.FitnessApp.repository.FitnessCenterRepository;
import com.collaborate.FitnessApp.services.FitnessCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FitnessCenterServiceImpl implements FitnessCenterService {

    private final FitnessCenterRepository fitnessCenterRepository;
    private final FitnessCenterDetailsRepository fitnessCenterDetailsRepository;


    @Autowired
    public FitnessCenterServiceImpl(FitnessCenterRepository fitnessCenterRepository, FitnessCenterDetailsRepository fitnessCenterDetailsRepository) {
        this.fitnessCenterRepository = fitnessCenterRepository;
        this.fitnessCenterDetailsRepository = fitnessCenterDetailsRepository;
    }

    @Override
    public FitnessCenterResponse register(FitnessCenterRequest request) {
        log.info("Registering new fitness center with email: {}", request.getEmailId());
        if (fitnessCenterRepository.existsByEmailId(request.getEmailId())) {
            log.error("Duplicate email: {}", request.getEmailId());
            throw new DuplicateResourceException("Fitness center already exists with email: " + request.getEmailId());
        }
        if (fitnessCenterRepository.existsByContactNo(request.getContactNo())) {
            log.error("Duplicate contact: {}", request.getContactNo());
            throw new DuplicateResourceException("Fitness center already exists with contact: " + request.getContactNo());
        }
        FitnessCenter entity = FitnessCenterMapper.toEntity(request);
        entity.setStatus(Status.ACTIVE);
        FitnessCenter saved = fitnessCenterRepository.save(entity);
        log.info("Fitness center registered with id: {}", saved.getId());
        return FitnessCenterMapper.toResponseDto(saved);
    }

    @Override
    public FitnessCenterResponse getById(Long id) {
        log.info("Fetching fitness center by id: {}", id);
        FitnessCenter entity = fitnessCenterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fitness center not found with id: " + id));
        return FitnessCenterMapper.toResponseDto(entity);
    }

    @Override
    public FitnessCenterResponse getByEmailId(String emailId) {
        log.info("Fetching fitness center by email: {}", emailId);
        FitnessCenter entity = fitnessCenterRepository.findByEmailId(emailId)
                .orElseThrow(() -> new ResourceNotFoundException("Fitness center not found with email: " + emailId));
        return FitnessCenterMapper.toResponseDto(entity);
    }

    @Override
    public FitnessCenterResponse getByContactNo(String contactNo) {
        log.info("Fetching fitness center by contact: {}", contactNo);
        FitnessCenter entity = fitnessCenterRepository.findByContactNo(contactNo)
                .orElseThrow(() -> new ResourceNotFoundException("Fitness center not found with contact: " + contactNo));
        return FitnessCenterMapper.toResponseDto(entity);
    }

    @Override
    public FitnessCenterResponse update(FitnessCenterRequest request) {
        log.info("Updating fitness center with id: {}", request.getId());
        FitnessCenter entity = fitnessCenterRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Fitness center not found with id: " + request.getId()));
        entity.setCenter_name(request.getCenter_name());
        entity.setEmailId(request.getEmailId());
        entity.setContactNo(request.getContactNo());
        entity.setPassword(request.getPassword());
        entity.setRole(request.getRole());
        FitnessCenter updated = fitnessCenterRepository.save(entity);
        log.info("Fitness center updated with id: {}", updated.getId());
        return FitnessCenterMapper.toResponseDto(updated);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting fitness center with id: {}", id);
        FitnessCenter entity = fitnessCenterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fitness center not found with id: " + id));
        fitnessCenterRepository.delete(entity);
        log.info("Fitness center deleted with id: {}", id);
    }

    @Override
    public List<FitnessCenterResponse> getByStatus(String status) {
        log.info("Fetching fitness centers by status: {}", status);
        List<FitnessCenter> centers = fitnessCenterRepository.findByStatus(Status.valueOf(status));
        return centers.stream().map(FitnessCenterMapper::toResponseDto).toList();
    }

    @Override
    public Page<FitnessCenterResponse> getFitnessCenters(List<Status> statuses, int page, int size) {
        log.info("Fetching paginated fitness centers by statuses: {}", statuses);
        Page<FitnessCenter> pageResult = fitnessCenterRepository.findByStatusIn(statuses, PageRequest.of(page, size, Sort.by("id").descending()));
        return pageResult.map(FitnessCenterMapper::toResponseDto);
    }
}
