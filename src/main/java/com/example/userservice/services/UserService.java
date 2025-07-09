package com.example.userservice.services;

import com.example.userservice.Repo.TokenRepo;
import com.example.userservice.Repo.UserRepo;
import com.example.userservice.models.Token;
import com.example.userservice.models.Users;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserService {

    private UserRepo userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepo tokenRepo;

    public UserService(UserRepo userRepo,TokenRepo tokenRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.tokenRepo = tokenRepo;
    }

   public Users signuUp(String name, String email, String password) throws Exception {
       Optional<Users> existingUser = userRepo.findByEmail(email);
       if (existingUser.isPresent()) {
           throw new Exception("User with this email already exists");
       }
       Users user = new Users();
       user.setName(name);
       user.setEmail(email);
       user.setPassword(bCryptPasswordEncoder.encode(password));
       user.setIsVerified(false);
       user.setCreatedAt(Instant.now().toEpochMilli());
       user.setUpdatedAt(Instant.now().toEpochMilli());
       userRepo.save(user);
       return user;
   }

   public Token login(String email, String password) throws Exception {
        Optional<Users> existingUser = userRepo.findByEmail(email);

        if (!existingUser.isPresent()) {
            throw new Exception("User with this email does not exist");
        }
        Users user = existingUser.get();
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new Exception("Email or password is incorrect");
        }
        Token token = new Token();
        token.setUser(user);
        token.setToken(RandomStringUtils.randomAlphanumeric(200));
        token.setExpiresAt(System.currentTimeMillis() + 3 * 24L * 60 * 60 * 1000);
        token.setCreatedAt(Instant.now().toEpochMilli());
        token.setUpdatedAt(Instant.now().toEpochMilli());
        tokenRepo.save(token);
        return token;
   }

   public void logout(String token) {
        Optional<Token> optionalToken = tokenRepo.findByToken(token);
        if (optionalToken.isEmpty()) {
            throw new RuntimeException("Token not found");
        }
        Token existingToken = optionalToken.get();
        existingToken.setDeleted(true);
        tokenRepo.save(existingToken);
   }

   public Users validateToken(String token) {
        Optional<Token> optionalToken = tokenRepo.findByTokenAndIsDeletedAndExpiresAtAfter(token,false,Instant.now().toEpochMilli());
        if (optionalToken.isEmpty()) {
            throw new RuntimeException("Token is invalid or expired");
        }
        Token userToken = optionalToken.get();
        return userToken.getUser();
   }

}
