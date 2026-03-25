package com.hamza.apikeymanager.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@JsonPropertyOrder({"id", "name", "keyValue", "owner", "createdAt"})
@Entity
@Table(name = "api_keys")
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String keyValue;
    private String owner;
    private LocalDateTime createdAt;

    public ApiKey() {}

    public ApiKey(String name, String keyValue, String owner) {
        this.name = name;
        this.keyValue = keyValue;
        this.owner = owner;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getKeyValue() { return keyValue; }
    public String getOwner() { return owner; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "ApiKey{id=" + id + ", name='" + name + "', owner='" + owner + "'}";
    }
}