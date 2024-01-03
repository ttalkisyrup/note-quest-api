package com.ttalksisyrup.note.quest.api.common.security.oauth;

import com.ttalksisyrup.note.quest.api.common.security.JwtProvider;
import com.ttalksisyrup.note.quest.api.common.type.JwtPair;
import com.ttalksisyrup.note.quest.api.domain.user.dto.OAuth2UserDto;
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
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        OAuth2Attribute oAuth2Attribute = getOAuth2Attribute(userRequest, oAuth2User);

        User tmpUser = userRepository
                .findByProviderAndProviderId(oAuth2Attribute.getProvider(), oAuth2Attribute.getProviderId())
                .orElseGet(() -> createUser(oAuth2Attribute));
        User user = userRepository.save(tmpUser);

        JwtPair jwtPair = jwtProvider.generateTokenPair(user.getId());
        user.setTokens(jwtPair);
        userRepository.save(user);

        return new OAuth2UserDto(user, jwtPair, oAuth2Attribute.getAttributes());
    }

    private OAuth2Attribute getOAuth2Attribute(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        return OAuth2Attribute.of(provider, attributes);
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
