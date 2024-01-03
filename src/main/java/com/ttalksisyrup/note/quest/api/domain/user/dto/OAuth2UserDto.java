package com.ttalksisyrup.note.quest.api.domain.user.dto;

import com.ttalksisyrup.note.quest.api.common.type.JwtPair;
import com.ttalksisyrup.note.quest.api.domain.user.entity.User;
import com.ttalksisyrup.note.quest.api.domain.user.type.UserRole;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@ToString
@AllArgsConstructor
public class OAuth2UserDto implements OAuth2User {
    private final User user;

    private JwtPair jwtPair;

    private final Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(UserRole.ROLES.USER));
    }

    @Override
    public String getName() {
        return user.getId().toString();
    }

    public JwtPair getJwtPair() {
        return jwtPair;
    }
}
