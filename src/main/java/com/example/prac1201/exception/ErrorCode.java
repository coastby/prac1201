package com.example.prac1201.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATED_USER_ID(HttpStatus.BAD_REQUEST, "동일한 아이디가 있습니다.");

    private HttpStatus httpStatus;
    private String message;

}
