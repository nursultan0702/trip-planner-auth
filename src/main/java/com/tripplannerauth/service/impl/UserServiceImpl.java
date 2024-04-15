package com.tripplannerauth.service.impl;

import com.tripplannerauth.model.UserResponse;
import com.tripplannerauth.repository.UserRepository;
import com.tripplannerauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public UserResponse getUserByEmail(String email) {
    var userByEmail = userRepository.findById(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found. Email: " + email));
    return UserResponse.builder()
        .email(userByEmail.getEmail())
        .firstName(userByEmail.getFirstName())
        .secondName(userByEmail.getSecondName())
        .build();
  }


}
