package com.example.prac1201.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponse {
    private String uId;
    private String token;
}
