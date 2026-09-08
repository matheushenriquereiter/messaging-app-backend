package org.example.messagingapp.dto;

import jakarta.validation.constraints.NotBlank;

public record SendMessageDTO(
        @NotBlank(message = "Username cannot be null or empty")
        String username,

        @NotBlank(message = "Message content cannot be null or empty")
        String content
) {
}
