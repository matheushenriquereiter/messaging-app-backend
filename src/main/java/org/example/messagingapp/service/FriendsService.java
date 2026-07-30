package org.example.messagingapp.service;

import org.example.messagingapp.dto.FriendUsernameDTO;
import org.example.messagingapp.exceptions.BusinessException;
import org.example.messagingapp.model.User;
import org.example.messagingapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FriendsService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public FriendsService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public void addFriend(String bearerToken, FriendUsernameDTO friendUsernameDTO) {
        String token = bearerToken.replace("Bearer ", "");

        User friend = userRepository.getUserByUsername(friendUsernameDTO.username())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Friend not found"));

        String userId = jwtService.extractAllClaims(token).getSubject();
        User user = userRepository.getUserById(Long.valueOf(userId))
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Authenticated user not found"));

        Set<User> friends =  user.getFriends();

        if (friends.contains(friend)) {
            throw new BusinessException(HttpStatus.CONFLICT, "Friend already added");
        }

        friends.add(friend);
        userRepository.save(user);
    }
}
