package com.tripplannerauth.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.tripplannerauth.entity.User;
import com.tripplannerauth.model.UserResponse;
import com.tripplannerauth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  public void testGetUserByEmail_UserExists() {
    String email = "user@example.com";
    User mockUser = new User(email, "John", "Doe", true);
    when(userRepository.findById(email)).thenReturn(Optional.of(mockUser));

    UserResponse expectedResponse = UserResponse.builder()
        .email(email)
        .firstName("John")
        .secondName("Doe")
        .build();

    UserResponse actualResponse = userService.getUserByEmail(email);
    assertNotNull(actualResponse);
    assertEquals(expectedResponse.email(), actualResponse.email());
    assertEquals(expectedResponse.firstName(), actualResponse.firstName());
    assertEquals(expectedResponse.secondName(), actualResponse.secondName());
  }

  @Test
  public void testGetUserByEmail_UserNotFound() {
    String email = "nonexistent@example.com";
    when(userRepository.findById(email)).thenReturn(Optional.empty());

    Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
      userService.getUserByEmail(email);
    });

    assertTrue(exception.getMessage().contains("User not found. Email: " + email));
  }
}
