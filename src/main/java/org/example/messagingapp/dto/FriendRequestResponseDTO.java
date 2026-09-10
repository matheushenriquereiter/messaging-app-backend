package org.example.messagingapp.dto;

import org.example.messagingapp.enums.FriendRequestStatus;

import java.time.Instant;

public record FriendRequestResponseDTO(
        UserResponseDTO sender,
        UserResponseDTO receiver,
        FriendRequestStatus status,
        Instant createdAt
) {
}
