package org.example.messagingapp.dto;

import jakarta.validation.constraints.NotEmpty;

public record FriendUsernameDTO(
        @NotEmpty(message = "Friend username cannot be null or empty")
        String username
) {
}
