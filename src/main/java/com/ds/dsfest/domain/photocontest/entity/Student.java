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
@Table(name = "students")
public class Student {

  @Id
  @Column(name = "student_id", length = 20)
  private String studentId;

  @Column(nullable = false, length = 50)
  private String studentName;
}
