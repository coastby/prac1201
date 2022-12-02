package com.example.prac1201.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    //토큰 생성하는 함수
    public String createToken(String uId, String key, long expiredTimeMs){
        Claims claims = Jwts.claims().setSubject(uId);  //claim 생성, sub 속성값 추가
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}
