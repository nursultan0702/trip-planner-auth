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

    @Override
    public UserResponse create(final UserRequest newUserRequest) {
      return null;
    }


}
