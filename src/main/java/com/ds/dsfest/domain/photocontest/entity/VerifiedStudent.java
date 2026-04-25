package com.ds.dsfest.domain.photocontest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "verified_students")
public class VerifiedStudent {

    @Id
    @Column(name = "hashed_identity", length = 64)
    private String hashedIdentity; // SHA-256 해시값 (64자)

    public VerifiedStudent(String hashedIdentity) {
        this.hashedIdentity = hashedIdentity;
    }
}
