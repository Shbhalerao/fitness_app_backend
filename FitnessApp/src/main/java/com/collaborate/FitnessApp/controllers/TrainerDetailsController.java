package com.collaborate.FitnessApp.controllers;

import com.collaborate.FitnessApp.domain.dto.requests.TrainerDetailsRequest;
import com.collaborate.FitnessApp.domain.dto.responses.TrainerDetailsResponse;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.collaborate.FitnessApp.services.TrainerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trainer-details")
public class TrainerDetailsController {

    private final TrainerDetailsService trainerDetailsService;

    @Autowired
    public TrainerDetailsController(TrainerDetailsService trainerDetailsService) {
        this.trainerDetailsService = trainerDetailsService;
    }

    @PostMapping
    public TrainerDetailsResponse create(@RequestBody TrainerDetailsRequest request) {
        return trainerDetailsService.create(request);
    }

    @GetMapping("/{id}")
    public TrainerDetailsResponse getById(@PathVariable Long id) {
        return trainerDetailsService.getById(id);
    }

    @PutMapping("/{id}")
    public TrainerDetailsResponse update(@PathVariable Long id, @RequestBody TrainerDetailsRequest request) {
        request.setId(id);
        return trainerDetailsService.update(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        trainerDetailsService.delete(id);
    }

    @GetMapping("/paged")
    public Page<TrainerDetailsResponse> getAllPaged(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return trainerDetailsService.getAll(page, size);
    }

    @GetMapping("/by-status")
    public Page<TrainerDetailsResponse> getByStatuses(
            @RequestParam(value = "statuses", required = false) List<Status> statuses,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return trainerDetailsService.getByStatuses(statuses, page, size);
    }
}
