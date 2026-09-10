package org.example.messagingapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.messagingapp.enums.FriendRequestStatus;

public record FriendRequestUpdateDTO(
        @NotEmpty(message = "Friend username cannot be null or empty")
        String username,

        @NotNull(message = "Friend request status cannot be null")
        FriendRequestStatus status
) {
}
