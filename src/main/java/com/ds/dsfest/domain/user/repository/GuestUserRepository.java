package com.ds.dsfest.domain.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ds.dsfest.domain.user.entity.GuestUser;

public interface GuestUserRepository extends JpaRepository<GuestUser, UUID> {}
