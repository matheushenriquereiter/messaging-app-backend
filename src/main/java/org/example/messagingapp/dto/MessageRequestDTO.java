package org.example.messagingapp.dto;

import jakarta.validation.constraints.NotBlank;

public record MessageRequestDTO(
        @NotBlank(message = "Message content cannot be null or empty")
        String content
) {
}
