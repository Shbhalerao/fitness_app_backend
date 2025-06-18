package com.collaborate.FitnessApp.controllers;

import com.collaborate.FitnessApp.domain.dto.requests.FitnessCenterRequest;
import com.collaborate.FitnessApp.domain.dto.responses.FitnessCenterResponse;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.collaborate.FitnessApp.services.FitnessCenterService;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/fitness-centers")
public class FitnessCenterController {

    private final FitnessCenterService fitnessCenterService;

    @Autowired
    public FitnessCenterController(FitnessCenterService fitnessCenterService) {
        this.fitnessCenterService = fitnessCenterService;
    }

    @PostMapping
    public ResponseEntity<FitnessCenterResponse> register(@RequestBody FitnessCenterRequest request) {
        FitnessCenterResponse response = fitnessCenterService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FitnessCenterResponse> getById(@PathVariable Long id) {
        FitnessCenterResponse response = fitnessCenterService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{emailId}")
    public ResponseEntity<FitnessCenterResponse> getByEmailId(@PathVariable String emailId) {
        FitnessCenterResponse response = fitnessCenterService.getByEmailId(emailId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/contact/{contactNo}")
    public ResponseEntity<FitnessCenterResponse> getByContactNo(@PathVariable String contactNo) {
        FitnessCenterResponse response = fitnessCenterService.getByContactNo(contactNo);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FitnessCenterResponse> update(@PathVariable Long id, @RequestBody FitnessCenterRequest request) {
        request.setId(id);
        FitnessCenterResponse response = fitnessCenterService.update(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fitnessCenterService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<FitnessCenterResponse>> getByStatus(@PathVariable String status) {
        List<FitnessCenterResponse> response = fitnessCenterService.getByStatus(status);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<FitnessCenterResponse>> getFitnessCenters(
            @RequestParam(value = "statuses" ,required = false) List<Status> statuses,
            @RequestParam(value = "page")int page,
            @RequestParam(value = "size") int size) {
        Page<FitnessCenterResponse> response = fitnessCenterService.getFitnessCenters(statuses, page, size);
        return ResponseEntity.ok(response);
    }
}
