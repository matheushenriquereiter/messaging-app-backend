package org.example.messagingapp.repository;

import org.example.messagingapp.enums.FriendRequestStatus;
import org.example.messagingapp.model.FriendRequest;
import org.example.messagingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    @Query("SELECT fr FROM FriendRequest fr WHERE (fr.sender = :user1 AND fr.receiver = :user2) OR (fr.sender = :user2 AND fr.receiver = :user1)")
    Optional<FriendRequest> getFriendRequestBetween(@Param("user1") User user1, @Param("user2") User user2);

    @Query("SELECT fr FROM FriendRequest fr WHERE (fr.sender = :user OR fr.receiver = :user) AND fr.status = :status")
    List<FriendRequest> getAllByUserAndStatus(@Param("user") User user, @Param("status") FriendRequestStatus status);
}
