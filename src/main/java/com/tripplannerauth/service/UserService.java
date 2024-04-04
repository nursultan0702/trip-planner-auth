package com.tripplannerauth.service;

import com.tripplannerauth.entity.User;
import com.tripplannerauth.model.UserRequest;
import com.tripplannerauth.model.UserResponse;

public interface UserService {
    UserResponse create(UserRequest userRequest);
}
