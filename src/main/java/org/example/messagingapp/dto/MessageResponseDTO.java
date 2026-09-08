package org.example.messagingapp.dto;

public record MessageResponseDTO(
        String content,
        String sender,
        String receiver
) {
}
