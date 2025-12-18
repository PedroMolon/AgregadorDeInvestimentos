package com.pedromolon.agregadordeinvestimentos.controller;

import com.pedromolon.agregadordeinvestimentos.dto.request.UserUpdateRequest;
import com.pedromolon.agregadordeinvestimentos.dto.response.UserResponse;
import com.pedromolon.agregadordeinvestimentos.security.JWTUserData;
import com.pedromolon.agregadordeinvestimentos.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> updateUser(
            @AuthenticationPrincipal JWTUserData user,
            @RequestBody @Valid UserUpdateRequest request
    ) {
        Long userId = user.userId();

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId, request));
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(
            @AuthenticationPrincipal JWTUserData user
    ) {
        Long userId = user.userId();
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
