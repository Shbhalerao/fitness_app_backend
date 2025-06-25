package com.collaborate.FitnessApp.controllers;

import com.collaborate.FitnessApp.domain.dto.requests.TrainerRequest;
import com.collaborate.FitnessApp.domain.dto.responses.TrainerResponse;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.collaborate.FitnessApp.services.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/v1/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    public ResponseEntity<TrainerResponse> register(@RequestBody TrainerRequest request) {
        TrainerResponse response = trainerService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.getById(id));
    }

    @GetMapping("/email/{emailId}")
    public ResponseEntity<TrainerResponse> getByEmailId(@PathVariable String emailId) {
        return ResponseEntity.ok(trainerService.getByEmailId(emailId));
    }

    @GetMapping("/contact/{contactNo}")
    public ResponseEntity<TrainerResponse> getByContactNo(@PathVariable String contactNo) {
        return ResponseEntity.ok(trainerService.getByContactNo(contactNo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainerResponse> update(@PathVariable Long id, @RequestBody TrainerRequest request) {
        request.setId(id);
        return ResponseEntity.ok(trainerService.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        trainerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TrainerResponse>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(trainerService.getByStatus(status));
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<TrainerResponse>> getPaged(
            @RequestParam(value = "statuses", required = false) List<Status> statuses,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
            Page<TrainerResponse> result = trainerService.getTrainers(statuses, page, size);
            return ResponseEntity.ok(result);
    }

    @GetMapping("/center/{centerId}")
    public ResponseEntity<Page<TrainerResponse>> getByCenterId(
            @PathVariable Long centerId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            Page<TrainerResponse> result = trainerService.getByCenterId(centerId, page, size);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
