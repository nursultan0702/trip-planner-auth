package com.tripplannerauth.service;

import com.tripplannerauth.model.UserResponse;

public interface UserService {
    UserResponse get(String email);
}
