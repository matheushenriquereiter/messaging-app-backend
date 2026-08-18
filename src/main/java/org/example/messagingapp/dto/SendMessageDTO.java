package org.example.messagingapp.dto;

import jakarta.validation.constraints.NotBlank;

public record SendMessageDTO(
        String content,

        @NotBlank(message = "User username cannot be null or empty")
        String username
) {
}
