package com.ttalksisyrup.note.quest.api.common.security.oauth;

import com.ttalksisyrup.note.quest.api.domain.user.type.UserProvider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class OAuth2Attribute {
    private Map<String, Object> attributes;
    private String email;
    private UserProvider provider;
    private String providerId;

    static OAuth2Attribute of(String provider, Map<String, Object> attributes) {
        System.out.println("OAuth2Attribute.of : " + provider);
        System.out.println(attributes);

        switch (provider) {
            case "google":
                return ofGoogle(attributes);
            case "naver":
                return ofNaver(attributes);
            default:
                throw new RuntimeException();
        }
    }

    private static OAuth2Attribute ofGoogle(Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .provider(UserProvider.GOOGLE)
                .providerId(String.valueOf(attributes.get("sub")))
                .email((String) attributes.get("email"))
                .build();
    }

    private static OAuth2Attribute ofNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
                .provider(UserProvider.NAVER)
                .providerId(String.valueOf(response.get("id")))
                .email((String) response.get("email"))
                .build();
    }
}
