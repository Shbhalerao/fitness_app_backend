package com.collaborate.FitnessApp.services.implementation;

import com.collaborate.FitnessApp.domain.dto.requests.TrainerDetailsRequest;
import com.collaborate.FitnessApp.domain.dto.responses.TrainerDetailsResponse;
import com.collaborate.FitnessApp.domain.entities.Trainer;
import com.collaborate.FitnessApp.domain.entities.TrainerDetails;
import com.collaborate.FitnessApp.domain.base.UserContext;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.collaborate.FitnessApp.exceptions.BadRequestException;
import com.collaborate.FitnessApp.exceptions.ResourceNotFoundException;
import com.collaborate.FitnessApp.mappers.TrainerDetailsMapper;
import com.collaborate.FitnessApp.repository.TrainerDetailsRepository;
import com.collaborate.FitnessApp.repository.TrainerRepository;
import com.collaborate.FitnessApp.services.TrainerDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerDetailsServiceImpl implements TrainerDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(TrainerDetailsServiceImpl.class);
    private final TrainerDetailsRepository trainerDetailsRepository;
    private final TrainerRepository trainerRepository;

    @Autowired
    public TrainerDetailsServiceImpl(TrainerDetailsRepository trainerDetailsRepository, TrainerRepository trainerRepository) {
        this.trainerDetailsRepository = trainerDetailsRepository;
        this.trainerRepository = trainerRepository;
    }

    @Override
    public TrainerDetailsResponse create(TrainerDetailsRequest request) {
        logger.info("Creating TrainerDetails for trainerId: {}", request.getTrainerId());
        Optional<Trainer> trainerOpt = trainerRepository.findById(request.getTrainerId());
        if (trainerOpt.isEmpty()) {
            logger.error("Trainer not found for id: {}", request.getTrainerId());
            throw new BadRequestException("Trainer not found for id: " + request.getTrainerId());
        }
        TrainerDetails entity = TrainerDetailsMapper.toEntity(request, trainerOpt.get());
        entity.setCreatedBy(UserContext.getAuditField());
        entity.setUpdatedBy(UserContext.getAuditField());
        TrainerDetails saved = trainerDetailsRepository.save(entity);
        logger.info("TrainerDetails created with id: {}", saved.getId());
        return TrainerDetailsMapper.toResponse(saved);
    }

    @Override
    public TrainerDetailsResponse getById(Long id) {
        logger.info("Fetching TrainerDetails by id: {}", id);
        TrainerDetails details = trainerDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrainerDetails not found for id: " + id));
        return TrainerDetailsMapper.toResponse(details);
    }


    @Override
    public TrainerDetailsResponse update(TrainerDetailsRequest request) {
        logger.info("Updating TrainerDetails with id: {}", request.getId());
        if (request.getId() == null) {
            logger.error("Null id in update request");
            throw new BadRequestException("Please provide id to update");
        }
        TrainerDetails existing = trainerDetailsRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No such TrainerDetails present for id: " + request.getId()));
        Optional<Trainer> trainerOpt = trainerRepository.findById(request.getTrainerId());
        if (trainerOpt.isEmpty()) {
            logger.error("Trainer not found for id: {}", request.getTrainerId());
            throw new BadRequestException("Trainer not found for id: " + request.getTrainerId());
        }
        TrainerDetails updated = TrainerDetailsMapper.toEntity(request, trainerOpt.get());
        updated.setUpdatedBy(UserContext.getAuditField());
        TrainerDetails saved = trainerDetailsRepository.save(updated);
        logger.info("TrainerDetails updated with id: {}", saved.getId());
        return TrainerDetailsMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting TrainerDetails with id: {}", id);
        if (!trainerDetailsRepository.existsById(id)) {
            logger.error("TrainerDetails not found for id: {}", id);
            throw new ResourceNotFoundException("No record available for id: " + id);
        }
        trainerDetailsRepository.deleteById(id);
        logger.info("TrainerDetails deleted with id: {}", id);
    }

    @Override
    public Page<TrainerDetailsResponse> getAll(int page, int size) {
        logger.info("Fetching all TrainerDetails paginated, page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<TrainerDetails> detailsPage = trainerDetailsRepository.findAll(pageable);
        return detailsPage.map(TrainerDetailsMapper::toResponse);
    }

    @Override
    public Page<TrainerDetailsResponse> getByStatuses(List<Status> statuses, int page, int size) {
        logger.info("Fetching TrainerDetails by statuses: {}, page: {}, size: {}", statuses, page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<TrainerDetails> detailsPage;
        if (statuses == null || statuses.isEmpty()) {
            detailsPage = trainerDetailsRepository.findAll(pageable);
        } else {
            detailsPage = trainerDetailsRepository.findAllByStatusIn(statuses, pageable);
        }
        return detailsPage.map(TrainerDetailsMapper::toResponse);
    }
}
