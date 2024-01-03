package com.ttalksisyrup.note.quest.api.domain.user.entity;

import com.ttalksisyrup.note.quest.api.common.model.entity.BaseTimeEntity;
import com.ttalksisyrup.note.quest.api.common.type.JwtPair;
import com.ttalksisyrup.note.quest.api.domain.user.type.UserProvider;
import com.ttalksisyrup.note.quest.api.domain.user.type.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, columnDefinition = "VARCHAR(255) COMMENT '회원 이메일'")
    private String email;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '회원 닉네임'")
    private String nickname;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '프로필 이미지 URL'")
    private String thumbnailImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) COMMENT '회원 권한'")
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) COMMENT '인증 업체'")
    private UserProvider provider;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) COMMENT '인증 업체 측 user의 고유 id'")
    private String providerId;

    @Column(columnDefinition = "VARCHAR(512) COMMENT '액세스 토큰'")
    private String accessToken;

    @Column(columnDefinition = "VARCHAR(512) COMMENT '리프래시 토큰'")
    private String refreshToken;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setTokens(JwtPair jwtPair) {
        setAccessToken(jwtPair.getAccessToken());
        setRefreshToken(jwtPair.getRefreshToken());
    }
}
