package com.example.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity(name="users")
public class Users extends BaseModel {
    private String name;
    private String email;
    private String password;  //This will be encrypted password
    @ManyToMany
    private List<Roles> roles;
    private Boolean  isVerified;
}
