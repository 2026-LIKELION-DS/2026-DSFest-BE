package com.ds.dsfest.domain.user.repository;

import com.ds.dsfest.domain.user.entity.GuestUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GuestUserRepository extends JpaRepository<GuestUser, UUID> {
    Optional<GuestUser> findById(UUID uuid);
}
