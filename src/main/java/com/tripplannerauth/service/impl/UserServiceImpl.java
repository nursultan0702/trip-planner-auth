package com.tripplannerauth.service.impl;

import com.tripplannerauth.exception.UserNotfoundException;
import com.tripplannerauth.model.UserResponse;
import com.tripplannerauth.repository.UserRepository;
import com.tripplannerauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse get(String email) {
        var user = userRepository.findById(email).orElseThrow(UserNotfoundException::new);
        return new UserResponse(user.getEmail(), user.getFirstName(), user.getLastName());
    }
}
