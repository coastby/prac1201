package com.example.prac1201.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private String uId;
    private String name;
}
