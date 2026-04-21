package com.ds.dsfest.domain.booth.service;

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

  private final BoothOperatingDayRepository boothOperatingDayRepository;
  private final BoothMapPositionRepository boothMapPositionRepository;

  public List<BoothListItemResDto> getBoothsByDay(int festivalDay) {
    return boothOperatingDayRepository.findAllByFestivalDayWithBooth(festivalDay).stream()
        .map(operatingDay -> BoothListItemResDto.from(operatingDay.getBooth(), operatingDay))
        .toList();
  }

  public List<BoothMapItemResDto> getBoothMap(int festivalDay, BoothType dayNightType) {
    return boothMapPositionRepository.findAllByDayAndType(festivalDay, dayNightType).stream()
        .map(BoothMapItemResDto::from)
        .toList();
  }
}
