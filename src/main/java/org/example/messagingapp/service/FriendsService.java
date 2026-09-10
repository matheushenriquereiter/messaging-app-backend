package org.example.messagingapp.service;

import lombok.RequiredArgsConstructor;
import org.example.messagingapp.dto.FriendRequestResponseDTO;
import org.example.messagingapp.dto.UsernameDTO;
import org.example.messagingapp.enums.FriendRequestStatus;
import org.example.messagingapp.exceptions.BusinessException;
import org.example.messagingapp.model.FriendRequest;
import org.example.messagingapp.model.User;
import org.example.messagingapp.repository.FriendRequestRepository;
import org.example.messagingapp.repository.UserRepository;
import org.example.messagingapp.util.FriendRequestUtil;
import org.example.messagingapp.util.UserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendsService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final FriendRequestRepository friendRequestRepository;

    public void sendFriendRequest(String bearerToken, UsernameDTO friendUsernameDTO) {
        String username = jwtService.extractUsername(bearerToken);

        User user = userRepository
                .getUserByUsername(username)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Authenticated user not found"));
        User friend = userRepository
                .getUserByUsername(friendUsernameDTO.username())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Friend not found"));

        FriendRequest friendRequest = new FriendRequest(user, friend, FriendRequestStatus.PENDING);

        friendRequestRepository.save(friendRequest);
    }

    public List<FriendRequestResponseDTO> getReceivedFriendRequests(String bearerToken) {
        String username = jwtService.extractUsername(bearerToken);

        User user = userRepository
                .getUserByUsername(username)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Authenticated user not found"));

        return user
                .getReceivedFriendRequests()
                .stream()
                .map(FriendRequestUtil::toResponseDTO)
                .toList();
    }
}
