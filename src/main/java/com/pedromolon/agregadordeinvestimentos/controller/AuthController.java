package com.pedromolon.agregadordeinvestimentos.controller;

import com.pedromolon.agregadordeinvestimentos.dto.request.LoginRequest;
import com.pedromolon.agregadordeinvestimentos.dto.request.RegisterRequest;
import com.pedromolon.agregadordeinvestimentos.dto.response.LoginResponse;
import com.pedromolon.agregadordeinvestimentos.dto.response.RegisterResponse;
import com.pedromolon.agregadordeinvestimentos.entity.User;
import com.pedromolon.agregadordeinvestimentos.entity.enums.Role;
import com.pedromolon.agregadordeinvestimentos.repository.UserRepository;
import com.pedromolon.agregadordeinvestimentos.security.TokenConfig;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenConfig tokenConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        User newUser = new User();
        newUser.setUsername(request.username());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));

        if (request.role() != null) {
            newUser.setRoles(Set.of(request.role()));
        } else {
            newUser.setRoles(Set.of(Role.ROLE_USER));
        }

        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(newUser.getUsername(), newUser.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        User user = (User) authentication.getPrincipal();
        String token = tokenConfig.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token));
    }

}
