package com.example.SnapLink.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UserResponseDTO {
    private String id;
    private  String username;
    private  String email;
    private Instant createdAt;
}
