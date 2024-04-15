package com.tripplannerauth.controller;

import com.tripplannerauth.model.UserResponse;
import com.tripplannerauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;


  @GetMapping("/{email}")
  public ResponseEntity<UserResponse> getUser(@PathVariable String email) {
    var userByEmail = userService.getUserByEmail(email);
    return ResponseEntity.ok(userByEmail);
  }
}
