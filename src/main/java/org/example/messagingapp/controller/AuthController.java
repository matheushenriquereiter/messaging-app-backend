package org.example.messagingapp.controller;

import jakarta.validation.Valid;
import org.example.messagingapp.dto.TokenDTO;
import org.example.messagingapp.dto.UserLoginDTO;
import org.example.messagingapp.dto.UserRegisterDTO;
import org.example.messagingapp.dto.UserResponseDTO;
import org.example.messagingapp.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        authService.signUp(userRegisterDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenDTO> signIn(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        TokenDTO tokenDTO = authService.signIn(userLoginDTO);

        return ResponseEntity.ok(tokenDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me(@RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");
        UserResponseDTO userResponseDTO = authService.me(token);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }
}
