package com.api.schedlr.schedlr_api_integration.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    public User() {
    }
    public User(int userid){
        this.userid = userid;
    }

    @Id
    private int userid;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType = AccountType.PERSONAL;

    // Constructors, getters, and setters

    public enum AccountType {
        PERSONAL, INFLUENCER
    }
}
