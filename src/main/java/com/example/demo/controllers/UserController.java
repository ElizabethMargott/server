package com.example.demo.controllers;

import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserUpdateDto;
import com.example.demo.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/current")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        UserResponseDto currentUserDto = userService.getCurrentUserDto();
        if (currentUserDto != null) {
            return ResponseEntity.ok(currentUserDto);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/current/avatar")
    public RedirectView getCurrentUserAvatarRedirect() {
        UserResponseDto currentUserDto = userService.getCurrentUserDto();
        if (currentUserDto != null) {
            String avatarFilename = currentUserDto.getAvatarFilename();
            return new RedirectView("/uploads/avatars/" + avatarFilename);
        }
        return new RedirectView("/uploads/avatars/default.png");
    }

    @PostMapping("/current/avatar")
    public ResponseEntity<?> updateUserAvatar(@RequestParam("avatar") MultipartFile avatar) {
        try {
            userService.updateUserAvatar(avatar);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the avatar: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody @Valid UserUpdateDto updatedUser) {
        UserResponseDto userDto = userService.updateUserById(updatedUser, id);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new MessageResponse("User with id " + id + " deleted"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User with id " + id + " not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Unable to delete user with id " + id));
        }
    }

    public class MessageResponse {
        private String message;

        public MessageResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
