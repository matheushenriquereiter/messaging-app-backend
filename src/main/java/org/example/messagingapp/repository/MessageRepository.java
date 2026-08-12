package org.example.messagingapp.repository;

import org.example.messagingapp.model.Message;
import org.example.messagingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m from Message m WHERE (m.sender = :user1 AND m.receiver = :user2) OR (m.receiver = :user2 AND m.sender = :user1)")
    List<Message> getMessages(User user1, User user2);
}
