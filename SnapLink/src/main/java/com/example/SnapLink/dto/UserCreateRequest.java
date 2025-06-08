package com.example.SnapLink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserCreateRequest {
    private String username;
    private String password;
    private String email;
} 