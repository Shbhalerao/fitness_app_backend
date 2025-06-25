package com.collaborate.FitnessApp.controllers;

import com.collaborate.FitnessApp.domain.dto.requests.TrainerDetailsRequest;
import com.collaborate.FitnessApp.domain.dto.responses.TrainerDetailsResponse;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.collaborate.FitnessApp.services.TrainerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TrainerDetailsResponse> create(@RequestBody TrainerDetailsRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(trainerDetailsService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerDetailsResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(trainerDetailsService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainerDetailsResponse> update(@PathVariable Long id, @RequestBody TrainerDetailsRequest request) {
        request.setId(id);
        return ResponseEntity.ok(trainerDetailsService.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        trainerDetailsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<TrainerDetailsResponse>> getAllPaged(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(trainerDetailsService.getAll(page, size));
    }

    @GetMapping("/by-status")
    public ResponseEntity<Page<TrainerDetailsResponse>> getByStatuses(
            @RequestParam(value = "statuses", required = false) List<Status> statuses,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(trainerDetailsService.getByStatuses(statuses, page, size));
    }
}
