package com.example.userservice.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private  String token;
    private String email;
    private String message;
    private ResponseStatus status;
    private Long expiresAt;
}
