package com.ds.dsfest.domain.photocontest.repository;

import com.ds.dsfest.domain.photocontest.entity.VerifiedStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifiedStudentRepository extends JpaRepository<VerifiedStudent, String> {
}
