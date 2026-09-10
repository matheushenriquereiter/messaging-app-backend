package org.example.messagingapp.service;

import lombok.RequiredArgsConstructor;
import org.example.messagingapp.dto.FriendRequestResponseDTO;
import org.example.messagingapp.dto.FriendRequestUpdateDTO;
import org.example.messagingapp.dto.UserResponseDTO;
import org.example.messagingapp.dto.UsernameDTO;
import org.example.messagingapp.enums.FriendRequestStatus;
import org.example.messagingapp.exceptions.BusinessException;
import org.example.messagingapp.model.FriendRequest;
import org.example.messagingapp.model.User;
import org.example.messagingapp.repository.FriendRequestRepository;
import org.example.messagingapp.repository.UserRepository;
import org.example.messagingapp.util.FriendRequestUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendsService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final FriendRequestRepository friendRequestRepository;

    public List<UserResponseDTO> getUserFriends(String bearerToken) {
        String username = jwtService.extractUsername(bearerToken);

        User user = userRepository
                .getUserByUsername(username)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Authenticated user not found"));

        List<FriendRequest> acceptedFriendRequests = friendRequestRepository.getAllByUserAndStatus(user, FriendRequestStatus.ACCEPTED);

        return acceptedFriendRequests
                .stream()
                .map(acceptedFriendRequest -> {
                    if (acceptedFriendRequest.getSender().equals(user)) {
                        return new UserResponseDTO(acceptedFriendRequest.getReceiver().getUsername(), acceptedFriendRequest.getReceiver().getEmail());
                    }

                    return new UserResponseDTO(acceptedFriendRequest.getSender().getUsername(), acceptedFriendRequest.getSender().getEmail());
                })
                .toList();
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

    public void sendFriendRequest(String bearerToken, UsernameDTO friendUsernameDTO) {
        String username = jwtService.extractUsername(bearerToken);

        User user = userRepository
                .getUserByUsername(username)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Authenticated user not found"));
        User friend = userRepository
                .getUserByUsername(friendUsernameDTO.username())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Friend not found"));

        if (user.equals(friend)) {
            throw new BusinessException(HttpStatus.CONFLICT, "Cannot send a friend request to yourself");
        }

        Optional<FriendRequest> friendRequest = friendRequestRepository.findBySenderAndReceiver(user, friend);
        if (friendRequest.isPresent()) {
            throw new BusinessException(HttpStatus.CONFLICT, "Friend request already sent");
        }

        friendRequestRepository.save(new FriendRequest(user, friend, FriendRequestStatus.PENDING));
    }
}
