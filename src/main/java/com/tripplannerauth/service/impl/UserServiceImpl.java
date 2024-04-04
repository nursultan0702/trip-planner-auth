package com.tripplannerauth.service.impl;

import com.tripplannerauth.entity.User;
import com.tripplannerauth.model.UserRequest;
import com.tripplannerauth.model.UserResponse;
import com.tripplannerauth.repository.UserRepository;
import com.tripplannerauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(final UserRequest newUserRequest) {
        var userEntity = buildUserEntity(newUserRequest);

        var createdUser = userRepository.save(userEntity);

        return new UserResponse(createdUser.getEmail());
    }

    private User buildUserEntity(final UserRequest newUserRequest) {
        var encryptedPassword = passwordEncoder.encode(newUserRequest.password());
        return User.builder()
                .email(newUserRequest.email())
                .password(encryptedPassword)
                .build();
    }
}
