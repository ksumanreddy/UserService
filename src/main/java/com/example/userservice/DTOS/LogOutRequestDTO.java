package com.example.userservice.DTOS;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class LogOutRequestDTO {
    private String token;
}
