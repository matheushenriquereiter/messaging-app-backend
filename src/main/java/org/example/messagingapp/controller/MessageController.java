package org.example.messagingapp.controller;

import org.example.messagingapp.dto.MessageRequestDTO;
import org.example.messagingapp.dto.MessageResponseDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MessageController {
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageResponseDTO send(MessageRequestDTO message) {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new MessageResponseDTO(message.from(), message.text(), time);
    }
}
