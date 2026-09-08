package org.example.messagingapp.service;

import lombok.RequiredArgsConstructor;
import org.example.messagingapp.dto.MessageResponseDTO;
import org.example.messagingapp.dto.SendMessageDTO;
import org.example.messagingapp.dto.UsernameDTO;
import org.example.messagingapp.exceptions.BusinessException;
import org.example.messagingapp.model.Message;
import org.example.messagingapp.model.User;
import org.example.messagingapp.repository.MessageRepository;
import org.example.messagingapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public List<MessageResponseDTO> getMessages(String bearerToken, UsernameDTO friendUsernameDTO) {
        String username = jwtService.extractUsername(bearerToken);
        User user = userRepository.getUserByUsername(username).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "User not found"));
        User friend = userRepository.getUserByUsername(friendUsernameDTO.username()).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Friend not found"));

        return messageRepository
                .getMessages(user, friend)
                .stream()
                .map(message -> new MessageResponseDTO(message.getContent(), message.getSender().getUsername(), message.getReceiver().getUsername()))
                .toList();
    }

    public void sendMessage(String bearerToken, SendMessageDTO sendMessageDTO) {
        String username = jwtService.extractUsername(bearerToken);
        User user = userRepository.getUserByUsername(username).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "User not found"));
        User friend = userRepository.getUserByUsername(sendMessageDTO.username()).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Friend not found"));

        Message message = new Message(sendMessageDTO.content(), user, friend);

        messageRepository.save(message);
    }
}
