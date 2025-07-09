package com.example.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="tokens")
public class Token extends BaseModel {
    private String token;
    @ManyToOne
    private Users user;
    private Long expiresAt;
}
