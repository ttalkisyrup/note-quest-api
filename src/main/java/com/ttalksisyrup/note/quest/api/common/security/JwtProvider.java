package com.ttalksisyrup.note.quest.api.common.security;

import com.ttalksisyrup.note.quest.api.common.type.JwtPair;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;
    private Key secretKey;

    public JwtPair generateTokenPair(long id) {
        return new JwtPair(accessTokenBuilder(id), refreshTokenBuilder());
    }

    @PostConstruct
    private void init() {
        byte[] secretKeyBytes = SECRET_KEY.getBytes();
        secretKey = new SecretKeySpec(secretKeyBytes, 0, secretKeyBytes.length, "HmacSHA256");
    }

    /**
     * 사용자의 id, 만료 시간을 인자로 액세스 토큰을 생성합니다.
     * 토큰의 payLoad 저장 값 = key :"uid" | value : 사용자의 id.
     *
     * @param id 유저 ID
     * @return String 형태의 토큰을 반환합니다.
     */
    private String accessTokenBuilder(Long id) {
        Claims claims = Jwts.claims()
                .subject("accessToken")
                .add("uid", id)
                .build();

        return builder(claims, TokenDuration.ACCESS_TOKEN_VALID_DURATION.duration);
    }

    /**
     * 리프레시 토큰을 생성합니다. 토큰의 payLoad 저장 값은 없습니다.
     *
     * @return String 형태의 토큰을 반환합니다.
     */
    private String refreshTokenBuilder() {
        Claims claims = Jwts.claims()
                .subject("refreshToken")
                .build();

        return builder(claims, TokenDuration.REFRESH_TOKEN_VALID_DURATION.duration);
    }

    private String builder(Claims claims, long validDuration) {
        Date now = new Date();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + validDuration))
                .signWith(secretKey)
                .compact();
    }

    @AllArgsConstructor
    enum TokenDuration {
        /**
         * AccessToken 1시간 토큰 유효
         */
        ACCESS_TOKEN_VALID_DURATION(1000L * 60 * 60 * 1),

        /**
         * 1달 토큰 유효
         */
        REFRESH_TOKEN_VALID_DURATION(1000L * 60 * 60 * 24 * 30);

        private final Long duration;
    }
}
