package org.example.messagingapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.messagingapp.dto.SendMessageDTO;
import org.example.messagingapp.dto.UsernameDTO;
import org.example.messagingapp.exceptions.BusinessException;
import org.example.messagingapp.model.Message;
import org.example.messagingapp.model.User;
import org.example.messagingapp.repository.MessageRepository;
import org.example.messagingapp.repository.UserRepository;
import org.example.messagingapp.service.JwtService;
import org.example.messagingapp.util.BearerTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @GetMapping
    public ResponseEntity<List<Message>> getMessages(@RequestHeader("Authorization") String bearerToken, @Valid @RequestBody UsernameDTO friendUsernameDTO) {
        String token = BearerTokenUtil.extractToken(bearerToken);
        String username = jwtService.extractAllClaims(token).getSubject();
        User user = userRepository.getUserByUsername(username).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "User not found"));
        User friend = userRepository.getUserByUsername(friendUsernameDTO.username()).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Friend not found"));

        return ResponseEntity.ok(messageRepository.getMessages(user, friend));
    }

    @PostMapping
    public void sendMessage(@RequestHeader("Authorization") String bearerToken, @Valid @RequestBody SendMessageDTO sendMessageDTO) {
        String token = BearerTokenUtil.extractToken(bearerToken);
        String username = jwtService.extractAllClaims(token).getSubject();
        User user = userRepository.getUserByUsername(username).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "User not found"));
        User friend = userRepository.getUserByUsername(sendMessageDTO.username()).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Friend not found"));

        Message message = new Message(sendMessageDTO.content(), user, friend);

        messageRepository.save(message);
    }
}
