package org.example.messagingapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.messagingapp.dto.MessageResponseDTO;
import org.example.messagingapp.dto.SendMessageDTO;
import org.example.messagingapp.dto.UsernameDTO;
import org.example.messagingapp.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<List<MessageResponseDTO>> getMessages(@RequestHeader("Authorization") String bearerToken, @Valid @RequestBody UsernameDTO friendUsernameDTO) {
        return ResponseEntity.ok(messageService.getMessages(bearerToken, friendUsernameDTO));
    }

    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestHeader("Authorization") String bearerToken, @Valid @RequestBody SendMessageDTO sendMessageDTO) {
        messageService.sendMessage(bearerToken, sendMessageDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
