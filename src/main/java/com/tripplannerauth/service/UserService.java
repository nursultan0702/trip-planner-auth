package com.tripplannerauth.service;

import com.tripplannerauth.model.UserResponse;

public interface UserService {
    UserResponse getUserByEmail(String email);
}
