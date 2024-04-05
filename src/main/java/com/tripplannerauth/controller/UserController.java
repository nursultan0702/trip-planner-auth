package com.tripplannerauth.controller;

import com.tripplannerauth.model.UserResponse;
import com.tripplannerauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String username) {
        var user = userService.get(username);
        return ResponseEntity.ok(user);
    }
}
