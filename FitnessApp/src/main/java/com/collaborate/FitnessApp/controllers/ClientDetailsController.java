package com.collaborate.FitnessApp.controllers;

import com.collaborate.FitnessApp.domain.dto.requests.ClientDetailsRequest;
import com.collaborate.FitnessApp.domain.dto.responses.ClientDetailsResponse;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.collaborate.FitnessApp.services.ClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<ClientDetailsResponse> getClientsPaged(
            @RequestParam(value = "statuses", required = false) List<Status> statuses,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return clientDetailsService.getClients(statuses, page, size);
    }

    @PostMapping
    public ClientDetailsResponse create(@RequestBody ClientDetailsRequest request) {
        return clientDetailsService.create(request);
    }

    @GetMapping("/by-client/{clientId}")
    public ClientDetailsResponse getByClientId(@PathVariable Long clientId) {
        return clientDetailsService.getByClientId(clientId);
    }

    @GetMapping("/{id}")
    public ClientDetailsResponse getById(@PathVariable Long id) {
        return clientDetailsService.getById(id);
    }

    @GetMapping("/all")
    public List<ClientDetailsResponse> getAll() {
        return clientDetailsService.getAll();
    }

    @GetMapping("/by-status")
    public Page<ClientDetailsResponse> getByStatus(
            @RequestParam(value = "statuses", required = false) List<Status> statuses,
            Pageable pageable) {
        return clientDetailsService.getByStatus(statuses, pageable);
    }

    @PutMapping("/{id}")
    public ClientDetailsResponse update(@PathVariable Long id, @RequestBody ClientDetailsRequest request) {
        request.setId(id);
        return clientDetailsService.update(request);
    }

    @PatchMapping("/{id}/status")
    public boolean updateStatusById(@PathVariable Long id, @RequestParam String status) {
        return clientDetailsService.updateStatusById(status, id);
    }

    @PatchMapping("/client/{clientId}/status")
    public boolean updateStatusByClientId(@PathVariable Long clientId, @RequestParam String status) {
        return clientDetailsService.updateStatusByClientId(status, clientId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clientDetailsService.delete(id);
    }
}
