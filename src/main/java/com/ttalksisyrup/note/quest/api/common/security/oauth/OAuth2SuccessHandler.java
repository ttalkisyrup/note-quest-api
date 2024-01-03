package com.ttalksisyrup.note.quest.api.common.security.oauth;

import com.ttalksisyrup.note.quest.api.common.type.JwtPair;
import com.ttalksisyrup.note.quest.api.domain.user.dto.OAuth2UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${spring.security.redirect-uri}")
    private String REDIRECT_URL;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2UserDto userDetailDTO = (OAuth2UserDto) authentication.getPrincipal();
        JwtPair jwtPair = userDetailDTO.getJwtPair();

        String url = setRedirectUrl(REDIRECT_URL, jwtPair);

        response.sendRedirect(url);
    }

    private String setRedirectUrl(String redirectURL, JwtPair jwtPair) {
        String accessToken = jwtPair.getAccessToken();
        String refreshToken = jwtPair.getRefreshToken();
        return UriComponentsBuilder.fromUriString(redirectURL)
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();
    }
}
