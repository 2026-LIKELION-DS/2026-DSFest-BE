package com.ds.dsfest.domain.photocontest.entity;

import com.ds.dsfest.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "photo_participations")
public class PhotoParticipation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64, unique = true)
    private String voterKey;

    public PhotoParticipation(String voterKey) {
        this.voterKey = voterKey;
    }
}
