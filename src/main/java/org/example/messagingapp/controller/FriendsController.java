package org.example.messagingapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.messagingapp.dto.FriendRequestResponseDTO;
import org.example.messagingapp.dto.UsernameDTO;
import org.example.messagingapp.service.FriendsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendsController {
    private final FriendsService friendsService;

    @GetMapping("/requests")
    public ResponseEntity<List<FriendRequestResponseDTO>> getReceivedFriendRequests(@RequestHeader("Authorization") String bearerToken) {
        List<FriendRequestResponseDTO> receivedFriendRequests = friendsService.getReceivedFriendRequests(bearerToken);

        return ResponseEntity.ok(receivedFriendRequests);
    }

    @PostMapping("/requests")
    public ResponseEntity<Void> sendFriendRequest(@RequestHeader("Authorization") String bearerToken, @Valid @RequestBody UsernameDTO friendUsernameDTO) {
        friendsService.sendFriendRequest(bearerToken, friendUsernameDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
