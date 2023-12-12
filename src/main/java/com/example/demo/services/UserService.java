package com.example.demo.services;

import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserUpdateDto;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder, FileStorageService fileStorageService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
    }

    public List<UserResponseDto> getAllUsers() {
        List<UserModel> users = userRepository.findAll();
        return users.stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        return convertToResponseDto(user);
    }

    public UserResponseDto updateUserById(UserUpdateDto request, Long id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setModificationDate(LocalDateTime.now());
        UserModel updatedUser = userRepository.save(user);
        return convertToResponseDto(updatedUser);
    }

    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
        return true;
    }

    public UserResponseDto getCurrentUserDto() {
        UserModel currentUser = getCurrentUser();
        return currentUser != null ? convertToResponseDto(currentUser) : null;
    }

    public void updateUserAvatar(MultipartFile avatar) throws IOException {
        UserModel currentUser = getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("No user is logged in.");
        }

        String avatarFilename = fileStorageService.storeFile(avatar);
        currentUser.setAvatarUrl(avatarFilename);
        currentUser.setModificationDate(LocalDateTime.now());
        userRepository.save(currentUser);
    }

    public UserModel getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
        }
        throw new IllegalStateException("No user is currently authenticated.");
    }

    private UserResponseDto convertToResponseDto(UserModel user) {
        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setAvatarUrl(user.getAvatarUrl());
        userDto.setRole(user.getRole());
        userDto.setRegistrationDate(user.getRegistrationDate());
        userDto.setModificationDate(user.getModificationDate());
        return userDto;
    }
}
