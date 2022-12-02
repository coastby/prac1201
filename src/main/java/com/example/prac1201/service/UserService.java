package com.example.prac1201.service;

import com.example.prac1201.domain.User;
import com.example.prac1201.dto.UserLoginRequest;
import com.example.prac1201.dto.UserLoginResponse;
import com.example.prac1201.dto.UserRequest;
import com.example.prac1201.dto.UserResponse;
import com.example.prac1201.exception.ErrorCode;
import com.example.prac1201.exception.UserException;
import com.example.prac1201.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserResponse join(UserRequest userRequest) {
        //같은 유저가 있으면 예외발생
        userRepository.findByUId(userRequest.getUId())
                .ifPresent(user -> {throw new UserException(ErrorCode.DUPLICATED_USER_ID);});
        //DB에 넣을 때 비밀번호 암호화 하기
        User savedUser = userRepository.save(userRequest.toEntity(encoder.encode(userRequest.getPassword())));

        return new UserResponse(savedUser.getUId(), savedUser.getName());
    }

    public UserLoginResponse login(UserLoginRequest userLoginReauest) {
        return UserLoginResponse.builder()
                .uId()
                .token()
                .build();
    }
}
