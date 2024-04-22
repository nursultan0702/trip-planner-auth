package com.tripplannerauth.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.tripplannerauth.entity.User;
import com.tripplannerauth.exception.UserNotFoundException;
import com.tripplannerauth.model.UserResponse;
import com.tripplannerauth.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  void testGetUserByEmail_UserExists() {
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
  void testGetUserByEmail_UserNotFound() {
    String email = "nonexistent@example.com";
    when(userRepository.findById(email)).thenReturn(Optional.empty());

    Exception exception =
        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(email));

    assertTrue(
        exception.getMessage().contains(String.format("User with email %s not found", email)));
  }
}
