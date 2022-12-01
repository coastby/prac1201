package com.example.prac1201.service;

import com.example.prac1201.domain.User;
import com.example.prac1201.dto.UserRequest;
import com.example.prac1201.dto.UserResponse;
import com.example.prac1201.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserResponse join(UserRequest userRequest) {
        //같은 유저가 있으면 예외발생
        userRepository.findByUId(userRequest.getUId())
                .ifPresent(user -> {throw new RuntimeException("같은 아이디가 있습니다.");});
        User savedUser = userRepository.save(userRequest.toEntity());
        //DB에 넣을 때 비밀번호 암호화 하기

        return new UserResponse(savedUser.getUId(), savedUser.getName());
    }
}
