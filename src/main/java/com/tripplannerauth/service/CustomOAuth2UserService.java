package com.tripplannerauth.service;

import com.tripplannerauth.entity.User;
import com.tripplannerauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  public static final String EMAIL = "email";
  public static final String GIVEN_NAME = "given_name";
  public static final String FAMILY_NAME = "family_name";

  private final UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User user = super.loadUser(userRequest);

    boolean userNotExist = isUserNotExist(user);

    if (userNotExist) {
      saveNewUser(user);
    }

    return user;
  }

  private boolean isUserNotExist(OAuth2User user) {
    String email = user.getAttribute(EMAIL);
    return !userRepository.existsByEmail(email);
  }

  private void saveNewUser(OAuth2User user) {

    var newUser = User.builder()
        .email(user.getAttribute(EMAIL))
        .firstName(user.getAttribute(GIVEN_NAME))
        .secondName(user.getAttribute(FAMILY_NAME))
        .isGoogleAccount(true)
        .build();

    userRepository.save(newUser);
  }
}
