package com.collaborate.FitnessApp.controllers;

import com.collaborate.FitnessApp.domain.dto.requests.ClientRequest;
import com.collaborate.FitnessApp.domain.dto.responses.ClientResponse;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.collaborate.FitnessApp.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@RequestBody ClientRequest clientRequest) {
        return ResponseEntity.status(201).body(clientService.register(clientRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getById(id));
    }

    @GetMapping("/email/{emailId}")
    public ResponseEntity<ClientResponse> getClientByEmail(@PathVariable String emailId) {
        return ResponseEntity.ok(clientService.getByEmailId(emailId));
    }

    @GetMapping("/contact/{contactNo}")
    public ResponseEntity<ClientResponse> getClientByContact(@PathVariable String contactNo) {
        return ResponseEntity.ok(clientService.getByContactNo(contactNo));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ClientResponse>> getClientsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(clientService.getByStatus(status));
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<ClientResponse>> getClientsPaged(
            @RequestParam(value = "statuses", required = false) List<Status> statuses,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(clientService.getClients(statuses, page, size));
    }

    @PutMapping
    public ResponseEntity<ClientResponse> updateClient(@RequestBody ClientRequest clientRequest) {
        return ResponseEntity.ok(clientService.update(clientRequest));
    }

    @PatchMapping("/status/email")
    public ResponseEntity<Void> updateStatusByEmail(@RequestParam String emailId, @RequestParam String status) {
        clientService.updateStatusByEmailId(status, emailId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/status/contact")
    public ResponseEntity<Void> updateStatusByContact(@RequestParam String contactNo, @RequestParam String status) {
        clientService.updateStatusByContactNo(status, contactNo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
