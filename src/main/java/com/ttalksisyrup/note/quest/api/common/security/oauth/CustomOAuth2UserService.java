package com.ttalksisyrup.note.quest.api.common.security.oauth;

import com.ttalksisyrup.note.quest.api.domain.user.entity.User;
import com.ttalksisyrup.note.quest.api.domain.user.repository.UserRepository;
import com.ttalksisyrup.note.quest.api.domain.user.type.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("OAuth2User: " + userRequest);

        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(provider, attributes);

        User user = userRepository
                .findByProviderAndProviderId(oAuth2Attribute.getProvider(), oAuth2Attribute.getProviderId())
                .orElseGet(() -> createUser(oAuth2Attribute));

        // TODO 유저의 id와 email을 가지고 토큰 발급

        // TODO refreshToken 업데이트

        userRepository.save(user);

        return oAuth2User;
    }

    private User createUser(OAuth2Attribute oAuth2Attribute) {
        return User.builder()
                .email(oAuth2Attribute.getEmail())
                .provider(oAuth2Attribute.getProvider())
                .providerId(oAuth2Attribute.getProviderId())
                .userRole(UserRole.USER)
                .build();
    }
}
