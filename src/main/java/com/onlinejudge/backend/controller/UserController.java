package com.onlinejudge.backend.controller;

import com.onlinejudge.backend.payload.request.UserUpdateRequest;
import com.onlinejudge.backend.payload.APIResponse;
import com.onlinejudge.backend.payload.response.UserProfileResponse;
import com.onlinejudge.backend.payload.response.UserPublicProfileResponse;
import com.onlinejudge.backend.repository.UserRepository;
import com.onlinejudge.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    // ✅ GET /api/users/me
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new APIResponse("Login required to access this resource.", false));
        }
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }

    // ✅ PUT /api/users/me
    @PutMapping("/me")
    public ResponseEntity<APIResponse> updateProfile(@RequestBody UserUpdateRequest request,
                                                     Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new APIResponse("Login required to access this resource.", false));
        }
        userService.updateCurrentUserProfile(request);
        return ResponseEntity.ok(new APIResponse("Profile updated successfully", true));
    }

    // 🌐 GET /api/users/{username}
    @GetMapping("/{username}")
    public ResponseEntity<UserPublicProfileResponse> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getPublicProfileByUsername(username));
    }
}
