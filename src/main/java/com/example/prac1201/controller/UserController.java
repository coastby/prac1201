package com.example.prac1201.controller;

import com.example.prac1201.dto.UserRequest;
import com.example.prac1201.dto.UserResponse;
import com.example.prac1201.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> join(@RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.join(userRequest);
        return ResponseEntity.ok().body(userResponse);
    }
}
