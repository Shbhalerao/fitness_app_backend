package com.collaborate.FitnessApp.controllers;

import com.collaborate.FitnessApp.domain.dto.requests.ClientDetailsRequest;
import com.collaborate.FitnessApp.domain.dto.responses.ClientDetailsResponse;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.collaborate.FitnessApp.services.ClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/client-details")
public class ClientDetailsController {

    private final ClientDetailsService clientDetailsService;

    @Autowired
    public ClientDetailsController(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<ClientDetailsResponse>> getClientsPaged(
            @RequestParam(value = "statuses", required = false) List<Status> statuses,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(clientDetailsService.getClients(statuses, page, size));
    }

    @PostMapping
    public ResponseEntity<ClientDetailsResponse> create(@RequestBody ClientDetailsRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientDetailsService.create(request));
    }

    @GetMapping("/by-client/{clientId}")
    public ResponseEntity<ClientDetailsResponse> getByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(clientDetailsService.getByClientId(clientId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDetailsResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(clientDetailsService.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientDetailsResponse>> getAll() {
        return ResponseEntity.ok(clientDetailsService.getAll());
    }

    @GetMapping("/by-status")
    public ResponseEntity<Page<ClientDetailsResponse>> getByStatus(
            @RequestParam(value = "statuses", required = false) List<Status> statuses,
            Pageable pageable) {
        return ResponseEntity.ok(clientDetailsService.getByStatus(statuses, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDetailsResponse> update(@PathVariable Long id, @RequestBody ClientDetailsRequest request) {
        request.setId(id);
        return ResponseEntity.ok(clientDetailsService.update(request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Boolean> updateStatusById(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(clientDetailsService.updateStatusById(status, id));
    }

    @PatchMapping("/client/{clientId}/status")
    public ResponseEntity<Boolean> updateStatusByClientId(@PathVariable Long clientId, @RequestParam String status) {
        return ResponseEntity.ok(clientDetailsService.updateStatusByClientId(status, clientId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientDetailsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
