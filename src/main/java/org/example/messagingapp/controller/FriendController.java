package org.example.messagingapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.messagingapp.dto.FriendRequestResponseDTO;
import org.example.messagingapp.dto.UserResponseDTO;
import org.example.messagingapp.dto.UsernameDTO;
import org.example.messagingapp.service.FriendService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getFriends(@RequestHeader("Authorization") String bearerToken) {
        List<UserResponseDTO> friends = friendService.getUserFriends(bearerToken);

        return ResponseEntity.ok(friends);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<FriendRequestResponseDTO>> getReceivedFriendRequests(@RequestHeader("Authorization") String bearerToken) {
        List<FriendRequestResponseDTO> receivedFriendRequests = friendService.getReceivedFriendRequests(bearerToken);

        return ResponseEntity.ok(receivedFriendRequests);
    }

    @PostMapping("/requests")
    public ResponseEntity<Void> sendFriendRequest(@RequestHeader("Authorization") String bearerToken, @Valid @RequestBody UsernameDTO friendUsernameDTO) {
        friendService.sendFriendRequest(bearerToken, friendUsernameDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
