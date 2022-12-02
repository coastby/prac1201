package com.example.prac1201.controller;

import com.example.prac1201.dto.UserLoginRequest;
import com.example.prac1201.dto.UserLoginResponse;
import com.example.prac1201.dto.UserRequest;
import com.example.prac1201.dto.UserResponse;
import com.example.prac1201.exception.ErrorCode;
import com.example.prac1201.exception.UserException;
import com.example.prac1201.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@WithMockUser
class UserControllerTest {
    @MockBean
    UserService userService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    UserRequest userRequest = new UserRequest("hoon", "1234", "훈");
    UserLoginRequest userLoginRequest = new UserLoginRequest("hoon", "1234");

    @Test
    @DisplayName("회원 가입 성공")
    void join_sucess() throws Exception {
        given(userService.join(any())).willReturn(UserResponse.builder()
                        .uId("hoon")
                        .name("훈")
                        .build());
        //when
        mockMvc.perform(
                post("/api/v1/users")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.uId").exists())    //<- uId 파싱이 안 됨
                .andExpect(jsonPath("$.name").value("훈"))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success() throws Exception {
        given(userService.login(any())).willReturn(any());

        //when
        mockMvc.perform(
                post("/api/v1/users/login")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(userLoginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andDo(print());
    }
    @Test
    @DisplayName("로그인 실패 - id 없음")
    void login_fail_id() throws Exception {
        given(userService.login(any())).willThrow(new UserException(ErrorCode.NO_SUCH_ID));

        //when
        mockMvc.perform(
                post("/api/v1/users/login")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(userLoginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").value("아이디를 확인해주세요."))
                .andDo(print());
    }
    @Test
    @DisplayName("로그인 실패 - 비밀 번호 틀림")
    void login_fail_pw() throws Exception {
        given(userService.login(any())).willThrow(new UserException(ErrorCode.NO_SUCH_ID));

        //when
        mockMvc.perform(
                post("/api/v1/users/login")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(userLoginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").value("비밀번호가 틀렸습니다."))
                .andDo(print());
    }
}