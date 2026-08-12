package org.example.messagingapp.dto;

import jakarta.validation.constraints.NotBlank;

public record UsernameDTO(
        @NotBlank(message = "User username cannot be null or empty")
        String username
) {
}
