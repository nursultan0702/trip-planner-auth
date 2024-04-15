package com.tripplannerauth.model;

import lombok.Builder;

@Builder
public record UserResponse(String email, String firstName, String secondName) {
}
