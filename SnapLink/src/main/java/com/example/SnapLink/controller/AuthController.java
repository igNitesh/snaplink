package com.example.SnapLink.controller;

import com.example.SnapLink.config.JwtUtil;
import com.example.SnapLink.dto.AuthReponse;
import com.example.SnapLink.dto.AuthRequest;
import com.example.SnapLink.dto.UserCreateRequest;
import com.example.SnapLink.dto.UserResponseDTO;
import com.example.SnapLink.entity.User;
import com.example.SnapLink.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private JwtUtil jwtUtil;
    @Autowired private  AuthService authService;
    @Autowired private  AuthenticationManager authManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserCreateRequest userCreateRequest) {
        if (authService.existsByUsername(userCreateRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        if (authService.existsByEmail(userCreateRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        UserResponseDTO user = authService.createUser(userCreateRequest);

        // after register i sent back jwt token so login automatically
        authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),userCreateRequest.getPassword()));
        User currUser = authService.loadUserByUsername(user.getUsername());
        String token = jwtUtil.generateToken(currUser.getUsername());

        return ResponseEntity.ok(AuthReponse.builder().token(token).username(user.getUsername()).build());
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            User user = authService.loadUserByUsername(request.getUsername());
            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok( AuthReponse.builder().token(token).username(request.getUsername()).build());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
