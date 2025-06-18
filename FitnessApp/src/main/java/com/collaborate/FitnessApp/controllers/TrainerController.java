package com.collaborate.FitnessApp.controllers;


import com.collaborate.FitnessApp.domain.dto.requests.TrainerRequest;
import com.collaborate.FitnessApp.domain.dto.responses.TrainerResponse;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.collaborate.FitnessApp.services.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    public TrainerResponse register(@RequestBody TrainerRequest request) {
        return trainerService.register(request);
    }

    @GetMapping("/{id}")
    public TrainerResponse getById(@PathVariable Long id) {
        return trainerService.getById(id);
    }

    @GetMapping("/email/{emailId}")
    public TrainerResponse getByEmailId(@PathVariable String emailId) {
        return trainerService.getByEmailId(emailId);
    }

    @GetMapping("/contact/{contactNo}")
    public TrainerResponse getByContactNo(@PathVariable String contactNo) {
        return trainerService.getByContactNo(contactNo);
    }

    @PutMapping("/{id}")
    public TrainerResponse update(@PathVariable Long id, @RequestBody TrainerRequest request) {
        request.setId(id);
        return trainerService.update(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        trainerService.delete(id);
    }

    @GetMapping("/status/{status}")
    public List<TrainerResponse> getByStatus(@PathVariable String status) {
        return trainerService.getByStatus(status);
    }

    @GetMapping("/paged")
    public Page<TrainerResponse> getPaged(
            @RequestParam(value = "statuses", required = false) List<Status> statuses,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return trainerService.getTrainers(statuses, page, size);
    }
}
