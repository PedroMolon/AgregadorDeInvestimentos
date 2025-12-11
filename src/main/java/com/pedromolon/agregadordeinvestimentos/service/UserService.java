package com.pedromolon.agregadordeinvestimentos.service;

import com.pedromolon.agregadordeinvestimentos.dto.request.UserRequest;
import com.pedromolon.agregadordeinvestimentos.dto.response.UserResponse;
import com.pedromolon.agregadordeinvestimentos.entity.User;
import com.pedromolon.agregadordeinvestimentos.exceptions.UserNotFoundException;
import com.pedromolon.agregadordeinvestimentos.mapper.UserMapper;
import com.pedromolon.agregadordeinvestimentos.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public UserResponse createUser(UserRequest request) {
        return userMapper.toResponse(
                userRepository.save(userMapper.toEntity(request))
        );
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        User.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .build();

        return userMapper.toResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

}
