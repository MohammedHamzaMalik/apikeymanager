package com.hamza.apikeymanager.repository;

import com.hamza.apikeymanager.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
}