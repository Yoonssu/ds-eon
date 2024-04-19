package com.aeon.hadog.base.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
public class JwtTokenProvider {

    @Value("${springboot.jwt.secret}")
    private static final String SECRET_KEY = "secretKey";

    public static String createToken(String loginId) {
        log.info("[createToken] 토큰 생성 시작");

        // Claim = Jwt Token에 들어갈 정보
        // Claim에 loginId를 넣어 줌으로써 나중에 loginId를 꺼낼 수 있음
        Claims claims = Jwts.claims();
        claims.put("loginId", loginId);

        Date expiryDate=Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

        String token =  Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(expiryDate)
                        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                        .compact();


        log.info("[createToken] 토큰 생성 완료");
        return token;
    }

    public static String getLoginId(String token) {
        log.info("[getLoginId] Claims에서 loginId 값 추출");
        return extractClaims(token).get("loginId").toString();
    }

    public static boolean isExpired(String token) {
        log.info("[isExpired] Token 만료 시간 체크");
        Date expiredDate = extractClaims(token).getExpiration();
        return expiredDate.before(new Date());
    }


    private static Claims extractClaims(String token) {
        log.info("[extractClaims] SecretKey를 사용해 Token Parsing");
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
