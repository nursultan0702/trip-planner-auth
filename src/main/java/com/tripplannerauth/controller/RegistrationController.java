package com.tripplannerauth.controller;

import com.tripplannerauth.model.UserRequest;
import com.tripplannerauth.model.UserResponse;
import com.tripplannerauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest newUser) {
        return new ResponseEntity<>(userService.create(newUser), HttpStatus.CREATED);
    }

}

