package com.onlinejudge.backend.service;

import com.onlinejudge.backend.exception.ResourceNotFoundException;
import com.onlinejudge.backend.model.User;
import com.onlinejudge.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.onlinejudge.backend.payload.request.UserUpdateRequest;
import com.onlinejudge.backend.payload.response.UserProfileResponse;
import com.onlinejudge.backend.payload.response.UserPublicProfileResponse;
import com.onlinejudge.backend.security.services.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "username", username));
    }
    public UserProfileResponse getCurrentUserProfile() {
        CustomUserDetails userDetails = (CustomUserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userDetails.getId()));

        return new UserProfileResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getContact(),
                user.getCountry(),
                user.getDescription(),
                user.getDate(),
                user.getRole().name()
        );
    }

    public void updateCurrentUserProfile(UserUpdateRequest updateRequest) {
        CustomUserDetails userDetails = (CustomUserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userDetails.getId()));

        // Update only allowed fields
        user.setFirstName(updateRequest.getFirstName());
        user.setLastName(updateRequest.getLastName());
        user.setContact(updateRequest.getContact());
        user.setCountry(updateRequest.getCountry());
        user.setDescription(updateRequest.getDescription());

        userRepository.save(user);
    }

    public UserPublicProfileResponse getPublicProfileByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return new UserPublicProfileResponse(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getCountry(),
                user.getDescription()
        );
    }
}
