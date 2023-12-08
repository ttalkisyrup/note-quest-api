package com.ttalksisyrup.note.quest.api.domain.pitch.entity;

import com.ttalksisyrup.note.quest.api.common.model.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "pitch")
@RequiredArgsConstructor
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Pitch extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(nullable = false)
    private Short octave;

    @Column(length = 20, nullable = false)
    private String clef;

    @Column(length = 200, nullable = false)
    private String imageUrl;
}
