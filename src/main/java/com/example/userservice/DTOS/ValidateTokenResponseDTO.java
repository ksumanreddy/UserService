package com.example.userservice.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenResponseDTO {
    private String message;
    private String email;
    private String username;
    private String token;
    private ResponseStatus responseStatus;
}
