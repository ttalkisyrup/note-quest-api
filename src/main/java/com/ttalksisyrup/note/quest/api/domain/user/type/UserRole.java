package com.ttalksisyrup.note.quest.api.domain.user.type;

import lombok.Getter;

@Getter
public enum UserRole {
    ANONYMOUS(ROLES.ANONYMOUS, "비로그인"),
    USER(ROLES.USER, "유저"),
    ADMIN(ROLES.ADMIN, "어드민");

    public static class ROLES {
        public static final String ANONYMOUS = "ANONYMOUS";
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
    }

    private final String role;
    private final String description;

    private UserRole(String role, String description) {
        this.role = role;
        this.description = description;
    }
}
