package com.example.userservice.Repo;

import com.example.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    Optional<Token> findByTokenAndIsDeletedAndExpiresAtAfter(String token,boolean isDeleted,long expiresAt);
}
