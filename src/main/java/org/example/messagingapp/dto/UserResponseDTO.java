package org.example.messagingapp.dto;

import jakarta.validation.constraints.NotBlank;

public record UserResponseDTO(
        @NotBlank(message = "User username cannot be null or empty")
        String username,

        @NotBlank(message = "User email cannot be null or empty")
        String email
) {
}
