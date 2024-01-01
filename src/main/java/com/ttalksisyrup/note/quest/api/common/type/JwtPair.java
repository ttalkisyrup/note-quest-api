package com.ttalksisyrup.note.quest.api.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class JwtPair {
    private String accessToken;
    private String refreshToken;
}
