package org.example.messagingapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.messagingapp.dto.FriendUsernameDTO;
import org.example.messagingapp.service.FriendsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friends")
public class FriendsController {
    private final FriendsService friendsService;

    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    @PostMapping
    public ResponseEntity<Void> addFriend(@RequestHeader("Authorization") String bearerToken, @Valid @RequestBody FriendUsernameDTO friendUsernameDTO) {
        friendsService.addFriend(bearerToken, friendUsernameDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
