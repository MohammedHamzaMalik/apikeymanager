package com.hamza.apikeymanager.service;

import com.hamza.apikeymanager.exception.ResourceNotFoundException;
import com.hamza.apikeymanager.model.ApiKey;
import com.hamza.apikeymanager.repository.ApiKeyRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ApiKeyService {

    private final ApiKeyRepository repository;

    public ApiKeyService(ApiKeyRepository repository) {
        this.repository = repository;
    }

    private String getCurrentUserEmail(){
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    public List<ApiKey> getAllKeys() {
        String email = getCurrentUserEmail();
        return repository.findByOwner(email);
    }

    public ApiKey getKeyById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("API key not found with id: "+id));
    }

    public ApiKey createKey(String name) {
        String email = getCurrentUserEmail();
        String generated = UUID.randomUUID().toString().replace("-", "");
        ApiKey key = new ApiKey(name, generated, email);
        return repository.save(key);
    }

    public void deleteKey(Long id) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("API key not found with id: "+id);
        }
        repository.deleteById(id);
    }
}