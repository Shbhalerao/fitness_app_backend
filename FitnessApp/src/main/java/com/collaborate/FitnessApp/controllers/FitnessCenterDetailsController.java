package com.collaborate.FitnessApp.controllers;

import com.collaborate.FitnessApp.domain.dto.requests.FitnessCenterDetailsRequest;
import com.collaborate.FitnessApp.domain.dto.responses.FitnessCenterDetailsResponse;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.collaborate.FitnessApp.services.FitnessCenterDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/fitness-center-details")
public class FitnessCenterDetailsController {

    private final FitnessCenterDetailsService fitnessCenterDetailsService;

    @Autowired
    public FitnessCenterDetailsController(FitnessCenterDetailsService fitnessCenterDetailsService) {
        this.fitnessCenterDetailsService = fitnessCenterDetailsService;
    }

    @PostMapping
    public ResponseEntity<FitnessCenterDetailsResponse> register(@RequestBody FitnessCenterDetailsRequest request) {
        FitnessCenterDetailsResponse response = fitnessCenterDetailsService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FitnessCenterDetailsResponse> getById(@PathVariable Long id) {
        FitnessCenterDetailsResponse response = fitnessCenterDetailsService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/center/{centerId}")
    public ResponseEntity<FitnessCenterDetailsResponse> getByCenterId(@PathVariable Long centerId) {
        FitnessCenterDetailsResponse response = fitnessCenterDetailsService.getByCenterId(centerId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FitnessCenterDetailsResponse> update(@PathVariable Long id, @RequestBody FitnessCenterDetailsRequest request) {
        request.setId(id);
        FitnessCenterDetailsResponse response = fitnessCenterDetailsService.update(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fitnessCenterDetailsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<FitnessCenterDetailsResponse>> getByStatus(@PathVariable String status) {
        List<FitnessCenterDetailsResponse> response = fitnessCenterDetailsService.getByStatus(status);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<FitnessCenterDetailsResponse>> getFitnessCenterDetails(
            @RequestParam(value = "statuses", required = false) List<Status> statuses,
            @RequestParam int page,
            @RequestParam int size) {
        Page<FitnessCenterDetailsResponse> response = fitnessCenterDetailsService.getFitnessCenterDetails(statuses, page, size);
        return ResponseEntity.ok(response);
    }
}
