package com.hamza.apikeymanager.repository;

import com.hamza.apikeymanager.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    List<ApiKey> findByOwner(String owner);
}