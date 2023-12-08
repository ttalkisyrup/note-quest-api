package com.ttalksisyrup.note.quest.api.domain.user.repository;

import com.ttalksisyrup.note.quest.api.domain.user.entity.User;
import com.ttalksisyrup.note.quest.api.domain.user.type.UserProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByProviderAndProviderId(UserProvider provider, String providerId);
}
