package com.ds.dsfest.domain.photocontest.repository;

import com.ds.dsfest.domain.photocontest.entity.PhotoContestSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoContestSettingRepository extends JpaRepository<PhotoContestSetting, Long> {
}
