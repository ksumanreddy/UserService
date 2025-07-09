package com.example.userservice.controllers;

import com.example.userservice.DTOS.*;
import com.example.userservice.DTOS.ResponseStatus;
import com.example.userservice.Repo.UserRepo;
import com.example.userservice.models.Token;
import com.example.userservice.models.Users;
import com.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("signup")
    public ResponseEntity<SignUpResponseDTO> signup(@RequestBody SignupRequestDTO signupRequestDTO) {
        String name = signupRequestDTO.getName();
        String email = signupRequestDTO.getEmail();
        String password = signupRequestDTO.getPassword();
        SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO();
        try{
            Users user = userService.signuUp(name, email, password);
            signUpResponseDTO.setEmail(user.getEmail());
            signUpResponseDTO.setMessage("Sign up successful");
            signUpResponseDTO.setStatus(ResponseStatus.SUCCESS);
            return ResponseEntity.ok(signUpResponseDTO);
        }catch (Exception e) {
            signUpResponseDTO.setMessage(e.getMessage());
            signUpResponseDTO.setStatus(ResponseStatus.FAILURE);
            return ResponseEntity.status(400).body(signUpResponseDTO);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String email = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        try{
            Token token = userService.login(email,password);
            loginResponseDTO.setEmail(email);
            loginResponseDTO.setStatus(ResponseStatus.SUCCESS);
            loginResponseDTO.setMessage("Login successful");
            loginResponseDTO.setExpiresAt(System.currentTimeMillis() + 3 * 24L * 60 * 60 * 1000);
            loginResponseDTO.setToken(token.getToken());
            return ResponseEntity.ok(loginResponseDTO);
        }catch (Exception e){
            loginResponseDTO.setMessage(e.getMessage());
            loginResponseDTO.setStatus(ResponseStatus.FAILURE);
            return ResponseEntity.status(400).body(loginResponseDTO);
        }
    }

    @PatchMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogOutRequestDTO logOutRequestDTO) {
          String token = logOutRequestDTO.getToken();
          try{
              userService.logout(token);
              return ResponseEntity.ok("Logout successful");
          }catch (Exception e) {
              return ResponseEntity.status(400).body("Error logging out");
          }
    }

    @GetMapping("/validate-token/{token}")
    public ResponseEntity<ValidateTokenResponseDTO> validateToken(@PathVariable("token") String token) {
        ValidateTokenResponseDTO tokenResponseDTO = new ValidateTokenResponseDTO();
        try{
            Users user = userService.validateToken(token);
            tokenResponseDTO.setEmail(user.getEmail());
            tokenResponseDTO.setMessage("Token validated successfully");
            tokenResponseDTO.setToken(token);
            tokenResponseDTO.setUsername(user.getName());
            tokenResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            return ResponseEntity.ok(tokenResponseDTO);
        }catch (Exception e) {
            tokenResponseDTO.setMessage(e.getMessage());
            tokenResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
            return ResponseEntity.status(400).body(tokenResponseDTO);
        }
    }

}