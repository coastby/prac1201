package com.example.prac1201.dto;

import com.example.prac1201.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequest {
    private String uId;
    private String password;
    private String name;
    public User toEntity(String password){
        return User.builder()
                .uId(this.uId)
                .name(this.name)
                .password(password)
                .build();
    }
}
