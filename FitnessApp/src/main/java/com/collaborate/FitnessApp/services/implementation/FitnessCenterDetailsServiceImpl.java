package com.collaborate.FitnessApp.services.implementation;

import com.collaborate.FitnessApp.domain.dto.requests.FitnessCenterDetailsRequest;
import com.collaborate.FitnessApp.domain.dto.responses.FitnessCenterDetailsResponse;
import com.collaborate.FitnessApp.domain.entities.FitnessCenter;
import com.collaborate.FitnessApp.domain.entities.FitnessCenterDetails;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.collaborate.FitnessApp.exceptions.DuplicateResourceException;
import com.collaborate.FitnessApp.exceptions.ResourceNotFoundException;
import com.collaborate.FitnessApp.mappers.FitnessCenterDetailsMapper;
import com.collaborate.FitnessApp.repository.FitnessCenterDetailsRepository;
import com.collaborate.FitnessApp.repository.FitnessCenterRepository;
import com.collaborate.FitnessApp.services.FitnessCenterDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FitnessCenterDetailsServiceImpl implements FitnessCenterDetailsService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FitnessCenterDetailsServiceImpl.class);
    private final FitnessCenterDetailsRepository detailsRepository;
    private final FitnessCenterRepository centerRepository;

    @Autowired
    public FitnessCenterDetailsServiceImpl(FitnessCenterDetailsRepository detailsRepository, FitnessCenterRepository centerRepository) {
        this.detailsRepository = detailsRepository;
        this.centerRepository = centerRepository;
    }

    @Override
    public FitnessCenterDetailsResponse register(FitnessCenterDetailsRequest request) {
        logger.info("Registering new fitness center details for centerId: {}", request.getCenterId());
        if (detailsRepository.existsByCenter_Id(request.getCenterId())) {
            logger.error("Duplicate details for centerId: {}", request.getCenterId());
            throw new DuplicateResourceException("Details already exist for centerId: " + request.getCenterId());
        }
        FitnessCenter center = centerRepository.findById(request.getCenterId())
                .orElseThrow(() -> new ResourceNotFoundException("Fitness center not found with id: " + request.getCenterId()));
        FitnessCenterDetails entity = FitnessCenterDetailsMapper.toEntity(request, center);
        entity.setStatus(Status.ACTIVE);
        FitnessCenterDetails saved = detailsRepository.save(entity);
        logger.info("Fitness center details registered with id: {}", saved.getId());
        return FitnessCenterDetailsMapper.toResponseDto(saved);
    }

    @Override
    public FitnessCenterDetailsResponse getById(Long id) {
        logger.info("Fetching fitness center details by id: {}", id);
        FitnessCenterDetails entity = detailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fitness center details not found with id: " + id));
        return FitnessCenterDetailsMapper.toResponseDto(entity);
    }

    @Override
    public FitnessCenterDetailsResponse getByCenterId(Long centerId) {
        logger.info("Fetching fitness center details by centerId: {}", centerId);
        FitnessCenterDetails entity = detailsRepository.findByCenter_Id(centerId)
                .orElseThrow(() -> new ResourceNotFoundException("Fitness center details not found for centerId: " + centerId));
        return FitnessCenterDetailsMapper.toResponseDto(entity);
    }

    @Override
    public FitnessCenterDetailsResponse update(FitnessCenterDetailsRequest request) {
        logger.info("Updating fitness center details with id: {}", request.getId());
        FitnessCenterDetails entity = detailsRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Fitness center details not found with id: " + request.getId()));
        FitnessCenter center = centerRepository.findById(request.getCenterId())
                .orElseThrow(() -> new ResourceNotFoundException("Fitness center not found with id: " + request.getCenterId()));
        entity.setCenter(center);
        entity.setAddressLine(request.getAddressLine());
        entity.setCity(request.getCity());
        entity.setState(request.getState());
        entity.setCountry(request.getCountry());
        entity.setPinCode(request.getPinCode());
        FitnessCenterDetails updated = detailsRepository.save(entity);
        logger.info("Fitness center details updated with id: {}", updated.getId());
        return FitnessCenterDetailsMapper.toResponseDto(updated);
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting fitness center details with id: {}", id);
        FitnessCenterDetails entity = detailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fitness center details not found with id: " + id));
        detailsRepository.delete(entity);
        logger.info("Fitness center details deleted with id: {}", id);
    }

    @Override
    public List<FitnessCenterDetailsResponse> getByStatus(String status) {
        logger.info("Fetching fitness center details by status: {}", status);
        List<FitnessCenterDetails> details = detailsRepository.findByStatus(Status.valueOf(status));
        return details.stream().map(FitnessCenterDetailsMapper::toResponseDto).toList();
    }

    @Override
    public Page<FitnessCenterDetailsResponse> getFitnessCenterDetails(List<Status> statuses, int page, int size) {
        logger.info("Fetching paginated fitness center details by statuses: {}", statuses);
        Page<FitnessCenterDetails> pageResult;
        if (statuses == null || statuses.isEmpty()) {
            pageResult = detailsRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
        } else {
            for (Status s : statuses) {
                if (!Status.isExactStatus(s.name())) {
                    throw new com.collaborate.FitnessApp.exceptions.BadRequestException("Invalid status: " + s);
                }
            }
            pageResult = detailsRepository.findByStatusIn(statuses, PageRequest.of(page, size, Sort.by("id").descending()));
            if (pageResult.isEmpty()) {
                throw new ResourceNotFoundException("No records found for status: " + statuses);
            }
        }
        if (pageResult.isEmpty()) {
            throw new ResourceNotFoundException("No records found");
        }
        return pageResult.map(FitnessCenterDetailsMapper::toResponseDto);
    }
}
