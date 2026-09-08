package org.example.messagingapp.dto;

import jakarta.validation.constraints.NotBlank;

public record UsernameDTO(
        @NotBlank(message = "Username cannot be null or empty")
        String username
) {
}
