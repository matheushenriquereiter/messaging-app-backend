package org.example.messagingapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.messagingapp.enums.FriendRequestStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "friend_requests")
@Getter
@Setter
@NoArgsConstructor
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friend_requests_seq_gen")
    @SequenceGenerator(name = "friend_requests_seq_gen", sequenceName = "friend_requests_seq", allocationSize = 1)
    private Long id;

    @NotNull(message = "Friend request sender cannot be null")
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @NotNull(message = "Friend request receiver cannot be null")
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @NotNull(message = "Friend request status cannot be null")
    @Enumerated(EnumType.STRING)
    private FriendRequestStatus status;

    @CreationTimestamp
    private Instant createdAt;

    public FriendRequest(User sender, User receiver, FriendRequestStatus status) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
    }
}
