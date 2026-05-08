package com.ds.dsfest.domain.photocontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ds.dsfest.domain.photocontest.entity.PhotoContestSetting;

public interface PhotoContestSettingRepository extends JpaRepository<PhotoContestSetting, Long> {}
