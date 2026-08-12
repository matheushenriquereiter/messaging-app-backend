package org.example.messagingapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq_gen")
    @SequenceGenerator(name = "message_seq_gen", sequenceName = "message_seq", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Message content cannot be null or empty")
    @Column(nullable = false, unique = true)
    private String content;

    @OneToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    User sender;

    @OneToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    User receiver;


    public Message(String content, User sender, User receiver) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
    }
}
