package com.ttalksisyrup.note.quest.api.domain.pitch.repository;

import com.ttalksisyrup.note.quest.api.domain.pitch.entity.Pitch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PitchRepository extends JpaRepository<Pitch, Long> {
}
