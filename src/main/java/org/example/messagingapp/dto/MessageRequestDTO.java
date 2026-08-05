package org.example.messagingapp.dto;

public record MessageRequestDTO(
        String from,
        String text
) {
}
