package org.example.messagingapp.service;

import org.example.messagingapp.dto.UserRegisterDTO;
import org.example.messagingapp.repository.FriendRequestRepository;
import org.example.messagingapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FriendServiceTest {
    @Mock
    private FriendRequestRepository friendRequestRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FriendService friendService;

    @Test
    void shouldAddFriendSuccessfully() {
        authService.signUp(new UserRegisterDTO("jorge", "jorge@gmail.com", "jorge123"));
    }
}
