package org.example.messagingapp.dto;

public record MessageResponseDTO(
        String from,
        String text,
        String time
) {
}
