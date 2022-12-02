package com.example.prac1201.controller;

import com.example.prac1201.dto.UserLoginRequest;
import com.example.prac1201.dto.UserLoginResponse;
import com.example.prac1201.dto.UserRequest;
import com.example.prac1201.dto.UserResponse;
import com.example.prac1201.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> join(@RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.join(userRequest);
        return ResponseEntity.ok().body(userResponse);
    }
    @PostMapping(value = "/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        UserLoginResponse userLoginResponse = userService.login(userLoginRequest);
        return ResponseEntity.ok().body(userLoginResponse);
    }
}
