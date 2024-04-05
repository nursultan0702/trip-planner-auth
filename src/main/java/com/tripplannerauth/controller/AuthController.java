package com.tripplannerauth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @GetMapping("/")
  public String getLoginInfo(OAuth2AuthenticationToken authentication, HttpServletRequest request,
                             HttpServletResponse response) {
    return Arrays.stream(request.getCookies())
        .filter(cookie -> "AUTH-TOKEN".equals(cookie.getName())).map(Cookie::getValue)
        .findFirst().orElse("Token not found in cookies");
  }
}
