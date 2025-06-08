package com.example.SnapLink.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthReponse {
    private String token;
    private String username;
}
