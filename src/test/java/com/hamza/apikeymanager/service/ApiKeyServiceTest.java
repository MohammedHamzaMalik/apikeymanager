package com.hamza.apikeymanager.service;

import com.hamza.apikeymanager.exception.ResourceNotFoundException;
import com.hamza.apikeymanager.model.ApiKey;
import com.hamza.apikeymanager.repository.ApiKeyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ApiKeyServiceTest {

    @Mock
    private ApiKeyRepository repository;

    @InjectMocks
    private ApiKeyService service;

    @BeforeEach
    void setupSecurityContext() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("hamza@test.com");
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
    }

    @Test
    void getAllKeys_returnsKeysForCurrentUser() {
        ApiKey key = new ApiKey("test-key", "abc123", "hamza@test.com");
        when(repository.findByOwner("hamza@test.com")).thenReturn(List.of(key));

        List<ApiKey> result = service.getAllKeys();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getOwner()).isEqualTo("hamza@test.com");
        verify(repository).findByOwner("hamza@test.com");
    }

    @Test
    void createKey_savesKeyWithCurrentUserAsOwner() {
        when(repository.save(any(ApiKey.class))).thenAnswer(i -> i.getArgument(0));

        ApiKey result = service.createKey("my-key");

        assertThat(result.getOwner()).isEqualTo("hamza@test.com");
        assertThat(result.getName()).isEqualTo("my-key");
        assertThat(result.getKeyValue()).isNotBlank();
        verify(repository).save(any(ApiKey.class));
    }

    @Test
    void deleteKey_throwsWhenKeyNotFound() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.deleteKey(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void getKeyById_throwsWhenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getKeyById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}