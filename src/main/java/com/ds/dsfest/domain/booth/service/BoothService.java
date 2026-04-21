package com.ds.dsfest.domain.booth.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.dsfest.domain.booth.constant.BoothType;
import com.ds.dsfest.domain.booth.dto.BoothListItemResDto;
import com.ds.dsfest.domain.booth.dto.BoothMapItemResDto;
import com.ds.dsfest.domain.booth.repository.BoothMapPositionRepository;
import com.ds.dsfest.domain.booth.repository.BoothOperatingDayRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoothService {

  private static final LocalTime DAY_START = LocalTime.of(0, 0);
  private static final LocalTime DAY_END = LocalTime.of(16, 0);
  private static final LocalTime NIGHT_START = LocalTime.of(16, 0);
  private static final LocalTime NIGHT_END = LocalTime.of(23, 59, 59);

  private final BoothOperatingDayRepository boothOperatingDayRepository;
  private final BoothMapPositionRepository boothMapPositionRepository;

  public List<BoothListItemResDto> getBoothsByDay(int festivalDay) {
    return boothOperatingDayRepository.findAllByFestivalDayWithBooth(festivalDay).stream()
        .map(operatingDay -> BoothListItemResDto.from(operatingDay.getBooth(), operatingDay))
        .toList();
  }

  public List<BoothListItemResDto> getBoothsByDayAndType(int festivalDay, BoothType type) {
    LocalTime from = type == BoothType.DAY ? DAY_START : NIGHT_START;
    LocalTime to = type == BoothType.DAY ? DAY_END : NIGHT_END;
    return boothOperatingDayRepository
        .findAllByFestivalDayAndTimeRange(festivalDay, from, to)
        .stream()
        .map(operatingDay -> BoothListItemResDto.from(operatingDay.getBooth(), operatingDay))
        .toList();
  }

  public List<BoothMapItemResDto> getBoothMap(int festivalDay, BoothType dayNightType) {
    return boothMapPositionRepository.findAllByDayAndType(festivalDay, dayNightType).stream()
        .map(BoothMapItemResDto::from)
        .toList();
  }
}
