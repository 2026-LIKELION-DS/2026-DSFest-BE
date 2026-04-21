package com.ds.dsfest.domain.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ds.dsfest.domain.schedule.entity.FestivalSchedule;

public interface FestivalScheduleRepository extends JpaRepository<FestivalSchedule, Long> {

  List<FestivalSchedule> findAllByOrderByFestivalDayAscStartTimeAsc();
}
