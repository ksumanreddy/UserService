package com.example.userservice.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDTO {
    private String message;
    private String email;
    private ResponseStatus status;
}
