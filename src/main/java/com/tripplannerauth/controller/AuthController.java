package com.tripplannerauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

@RestController
public class AuthController {

    @GetMapping("/loginSuccess")
    public String getLoginInfo(OAuth2AuthenticationToken authentication) {
        // Retrieve authentication details and issue a JWT or another token as needed
        return "Login Successful";
    }
}
