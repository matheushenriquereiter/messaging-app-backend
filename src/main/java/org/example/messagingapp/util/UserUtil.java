package org.example.messagingapp.util;

import org.example.messagingapp.dto.UserResponseDTO;
import org.example.messagingapp.model.User;

public class UserUtil {
    private UserUtil() {}

    public static UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(user.getUsername(), user.getEmail());
    }
}
