package org.example.messagingapp.service;

import lombok.RequiredArgsConstructor;
import org.example.messagingapp.dto.TokenDTO;
import org.example.messagingapp.dto.UserLoginDTO;
import org.example.messagingapp.dto.UserRegisterDTO;
import org.example.messagingapp.dto.UserResponseDTO;
import org.example.messagingapp.exceptions.BusinessException;
import org.example.messagingapp.model.User;
import org.example.messagingapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public void signUp(UserRegisterDTO userRegisterDTO) {
        Optional<User> userWithSameEmail = userRepository.getUserByEmail(userRegisterDTO.email());
        Optional<User> userWithSameUsername = userRepository.getUserByUsername(userRegisterDTO.username());

        if (userWithSameEmail.isPresent()) {
            throw new BusinessException(HttpStatus.CONFLICT, "Email already taken");
        }

        if (userWithSameUsername.isPresent()) {
            throw new BusinessException(HttpStatus.CONFLICT, "Username already taken");
        }

        String encodedPassword = passwordEncoder.encode(userRegisterDTO.password());
        User userToRegister = new User(userRegisterDTO.username(), userRegisterDTO.email(), encodedPassword);

        userRepository.save(userToRegister);
    }

    public TokenDTO signIn(UserLoginDTO userLoginDTO) {
        User user = userRepository.getUserByEmail(userLoginDTO.email()).orElseThrow(() -> new BusinessException(HttpStatus.BAD_REQUEST, "Invalid credentials"));

        if (!passwordEncoder.matches(userLoginDTO.password(), user.getPassword())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Invalid credentials");
        }

        return new TokenDTO(jwtService.generateAccessToken(String.valueOf(user.getId())));
    }

    public UserResponseDTO me(String token) {
        String userId = jwtService.extractAllClaims(token).getSubject();
        User user = userRepository.getUserById(Long.valueOf(userId)).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "User not found"));

        return new UserResponseDTO(user.getUsername(), user.getEmail());
    }
}
