package com.example.prac1201.service;

import com.example.prac1201.domain.User;
import com.example.prac1201.dto.UserLoginRequest;
import com.example.prac1201.dto.UserLoginResponse;
import com.example.prac1201.dto.UserRequest;
import com.example.prac1201.dto.UserResponse;
import com.example.prac1201.exception.ErrorCode;
import com.example.prac1201.exception.UserException;
import com.example.prac1201.repository.UserRepository;
import com.example.prac1201.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @Value("${jwt.token.secret}")
    private String key;
    private long expiredTime = 60 * 60 * 1000; //1시간

    public UserResponse join(UserRequest userRequest) {
        //같은 유저가 있으면 예외발생
        userRepository.findByUId(userRequest.getUId())
                .ifPresent(user -> {throw new UserException(ErrorCode.DUPLICATED_USER_ID);});
        //DB에 넣을 때 비밀번호 암호화 하기
        User savedUser = userRepository.save(userRequest.toEntity(encoder.encode(userRequest.getPassword())));

        return new UserResponse(savedUser.getUId(), savedUser.getName());
    }

    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        //아이디가 없을 때 예외 발생
        User user = userRepository.findByUId(userLoginRequest.getUId())
                .orElseThrow(() -> new UserException(ErrorCode.NO_SUCH_ID));
        //비밀번호가 틀렸을 때 예외 발생
        if(!encoder.matches(userLoginRequest.getPassword(), user.getPassword())){
            throw new UserException(ErrorCode.INCORRECT_PASSWORD);
        }
        //로그인 성공 시 토큰 발행
        String token = jwtUtil.createToken(user.getUId(), key, expiredTime);

        return UserLoginResponse.builder()
                .uId(user.getUId())
                .token(token)
                .build();
    }
}
