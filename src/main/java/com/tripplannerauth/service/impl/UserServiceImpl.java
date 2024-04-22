package com.tripplannerauth.service.impl;

import com.tripplannerauth.exception.UserNotFoundException;
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
  public UserResponse getUserByEmail(String email) {
    var userByEmail = userRepository.findById(email)
        .orElseThrow(() -> new UserNotFoundException(email));

    return UserResponse.builder()
        .email(userByEmail.getEmail())
        .firstName(userByEmail.getFirstName())
        .secondName(userByEmail.getSecondName())
        .build();
  }


}
