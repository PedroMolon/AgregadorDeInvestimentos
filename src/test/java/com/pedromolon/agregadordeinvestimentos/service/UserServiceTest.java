package com.pedromolon.agregadordeinvestimentos.service;

import com.pedromolon.agregadordeinvestimentos.dto.request.UserRequest;
import com.pedromolon.agregadordeinvestimentos.dto.response.UserResponse;
import com.pedromolon.agregadordeinvestimentos.entity.User;
import com.pedromolon.agregadordeinvestimentos.entity.enums.Role;
import com.pedromolon.agregadordeinvestimentos.exceptions.UserNotFoundException;
import com.pedromolon.agregadordeinvestimentos.mapper.UserMapper;
import com.pedromolon.agregadordeinvestimentos.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        user = new User(1L, "testUser", "test@email.com", "password", Collections.singleton(Role.ROLE_USER), null, null);
        userRequest = new UserRequest("newUser", "new@email.com", "password");
        userResponse = new UserResponse(1L, "newUser", "new@email.com", LocalDateTime.now());
    }

    @Test
    void shouldUpdateUserSuccessfully() throws Exception {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponse(any(User.class))).thenReturn(userResponse);

        var response = userService.updateUser(1L, userRequest);

        assertNotNull(response);
        assertEquals(userResponse.id(), response.id());
        assertEquals(userResponse.username(), response.username());
        assertEquals(userResponse.email(), response.email());

        verify(userRepository, times(1)).findById(1L);
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldThrowUserNotFoundExceptionOnUpdate() throws UserNotFoundException {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(1L, userRequest);
        });

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldDeleteUserSuccessfully() throws Exception {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(userRepository).deleteById(anyLong());

        userService.deleteUser(1L);

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowUserNotFoundExceptionOnDelete() {
        when(userRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(1L);
        });

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, never()).deleteById(anyLong());
    }

}
