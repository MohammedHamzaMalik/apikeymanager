package com.hamza.apikeymanager.controller;

import com.hamza.apikeymanager.model.ApiKey;
import com.hamza.apikeymanager.service.ApiKeyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keys")
public class ApiKeyController {

    private final ApiKeyService service;

    public ApiKeyController(ApiKeyService service) {
        this.service = service;
    }

    @GetMapping
    public List<ApiKey> getAllKeys() {
        return service.getAllKeys();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiKey> getKeyById(@PathVariable Long id){
        ApiKey key = service.getKeyById(id);
        return ResponseEntity.ok(key);
    }

    @PostMapping
    public ResponseEntity<ApiKey> createKey(@Valid @RequestBody CreateKeyRequest request) {
        ApiKey created = service.createKey(request.name(), request.owner());
        return ResponseEntity.status(201).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKey(@PathVariable Long id) {
        service.deleteKey(id);
        return ResponseEntity.noContent().build();
    }

    public record CreateKeyRequest(
        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotBlank(message = "Owner cannot be empty")
        String owner
    ) {}
}