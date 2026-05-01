package com.ds.dsfest.domain.photocontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ds.dsfest.domain.photocontest.entity.VerifiedStudent;

public interface VerifiedStudentRepository extends JpaRepository<VerifiedStudent, String> {}
