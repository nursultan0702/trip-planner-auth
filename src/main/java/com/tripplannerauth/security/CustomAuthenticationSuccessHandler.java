package com.tripplannerauth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler
    extends SavedRequestAwareAuthenticationSuccessHandler implements
    AuthenticationSuccessHandler {

  @Value("${app.jwtSecret}")
  private String secretKey;
  @Value("${app.jwtExpirationMs}")
  private Integer expirationInMs;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException {
    String token = Jwts.builder()
        .subject(authentication.getName())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + expirationInMs))
        .signWith(SignatureAlgorithm.HS256, getKey())
        .compact();

    Cookie authCookie = new Cookie("AUTH-TOKEN", token);
    authCookie.setHttpOnly(true);
    authCookie.setPath("/");
    response.addCookie(authCookie);
    getRedirectStrategy().sendRedirect(request, response, "/");
  }

  private Key getKey() {
    byte[] secretBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return new SecretKeySpec(secretBytes, "HmacSHA256");
  }
}
