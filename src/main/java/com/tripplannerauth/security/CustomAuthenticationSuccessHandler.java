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
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler
    extends SavedRequestAwareAuthenticationSuccessHandler {

  @Value("${app.jwtSecret}")
  private String secretKey;

  @Value("${app.jwtExpirationMs}")
  private Integer expirationInMs;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException {
    String token = generateToken(authentication);
    Cookie authCookie = createAuthCookie(token);
    response.addCookie(authCookie);
    getRedirectStrategy().sendRedirect(request, response, "/");
  }

  private String generateToken(Authentication authentication) {
    return Jwts.builder()
        .subject(getEmail(authentication))
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + expirationInMs))
        .signWith(SignatureAlgorithm.HS256, getKey())
        .compact();
  }

  private Cookie createAuthCookie(String token) {
    Cookie authCookie = new Cookie("AUTH-TOKEN", token);
    authCookie.setHttpOnly(true);
    authCookie.setPath("/");
    return authCookie;
  }

  private String getEmail(Authentication authentication) {
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    if (oAuth2User == null) {
      throw new UsernameNotFoundException("Authentication principal is not available.");
    }
    Map<String, Object> attributes = oAuth2User.getAttributes();
    if (!attributes.containsKey("email")) {
      throw new UsernameNotFoundException(
          "Email attribute is not available in the principal's attributes.");
    }
    return (String) attributes.get("email");
  }

  private Key getKey() {
    byte[] secretBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return new SecretKeySpec(secretBytes, "HmacSHA256");
  }
}
