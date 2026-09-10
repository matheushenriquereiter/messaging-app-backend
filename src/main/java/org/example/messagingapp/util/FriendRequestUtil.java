package org.example.messagingapp.util;


import org.example.messagingapp.dto.FriendRequestResponseDTO;
import org.example.messagingapp.model.FriendRequest;

public class FriendRequestUtil {
    private FriendRequestUtil() {
    }

    public static FriendRequestResponseDTO toResponseDTO(FriendRequest friendRequest) {
        return new FriendRequestResponseDTO(
                UserUtil.toResponseDTO(friendRequest.getSender()),
                UserUtil.toResponseDTO(friendRequest.getReceiver()),
                friendRequest.getStatus(),
                friendRequest.getCreatedAt()
        );
    }
}
