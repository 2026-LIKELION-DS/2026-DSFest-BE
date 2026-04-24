package com.ds.dsfest.domain.foodtruck.service;

import com.ds.dsfest.domain.foodtruck.dto.FoodTruckListResDto;
import com.ds.dsfest.domain.foodtruck.entity.FoodTruck;
import com.ds.dsfest.domain.foodtruck.entity.FoodTruckOperatingDay;
import com.ds.dsfest.domain.foodtruck.mapper.FoodTruckMapper;
import com.ds.dsfest.domain.foodtruck.repository.FoodTruckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.Clock;

/**
 * 푸드트럭 도메인의 비즈니스 로직을 담당하는 서비스 클래스입니다.
 * 푸드트럭 목록 조회 및 운영 상태(isOpen) 판별 등의 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodTruckService {

    private final FoodTruckRepository foodTruckRepository;
    private final FoodTruckMapper foodTruckMapper;
    private final Clock clock;

    /**
     * 현재 서버 시간과 DB의 운영 일차(festivalDay) 정보를 비교하여 푸드트럭 목록을 조회합니다.
     * 프론트엔드의 새로고침 버튼 대응 및 트럭 간 노출 형평성을 위해 조회된 리스트는 무작위로 섞어서 반환합니다.
     *
     * @return 운영 상태 및 오늘 일정이 포함된 푸드트럭 리스트 응답 DTO 목록
     */
    public List<FoodTruckListResDto> getFoodTruckList() {
        List<FoodTruck> foodTrucks = foodTruckRepository.findAllWithOperatingDays();

        LocalDateTime now = LocalDateTime.now(clock);
        LocalDate today = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        int currentFestivalDay = getFestivalDayFromDate(today); // 오늘이 축제 몇 일차인지 계산 (축제 기간 아니면 -1 반환)

        List<FoodTruckListResDto> resultList = foodTrucks.stream()
            .map(truck -> {
                /**
                 * 운영 리스트 중 '오늘(currentFestivalDay)'에 해당하는 일정 찾기
                 */
                Optional<FoodTruckOperatingDay> todaySchedule = truck.getOperatingDays().stream()
                    .filter(schedule -> schedule.getFestivalDay() == currentFestivalDay)
                    .findFirst();

                boolean isOpen = false;
                String operatingDaysString = "운영 정보 없음";

                /**
                 * 오늘 영업하는 트럭일 경우
                 */
                if (todaySchedule.isPresent()) {
                    FoodTruckOperatingDay schedule = todaySchedule.get();
                    LocalTime start = schedule.getStartTime();
                    LocalTime end = schedule.getEndTime();

                    String dayOfWeek = today.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
                    operatingDaysString = String.format("%d일(%s) %s - %s",
                        today.getDayOfMonth(),
                        dayOfWeek,
                        start.format(timeFormatter),
                        end.format(timeFormatter));

                    isOpen = !currentTime.isBefore(start) && !currentTime.isAfter(end); // 현재 시간과 비교하여 영업 여부(isOpen) 결정
                }

                Integer dummyLikeCount = 999; // 좋아요 구현 예정

                return foodTruckMapper.toFoodTruckListResDto(truck, operatingDaysString, dummyLikeCount, isOpen);
            })
            .collect(Collectors.toList());

        Collections.shuffle(resultList); // 새로고침 시 형평성 보장 위해 리스트 순서 랜덤하게 섞음

        return resultList;
    }

    /**
     * 실제 날짜(LocalDate)를 축제 일차(festivalDay: 1, 2, 3) 엔티티 구조로 변환합니다.
     *
     * @param date 변환할 기준 날짜 (주로 서버의 현재 날짜)
     * @return 축제 일차 (1, 2, 3), 축제 기간이 아닐 경우 -1 반환
     */
    private int getFestivalDayFromDate(LocalDate date) {
        if (date.equals(LocalDate.of(2026, 4, 24))) return 1;
        if (date.equals(LocalDate.of(2026, 5, 14))) return 2;
        if (date.equals(LocalDate.of(2026, 5, 15))) return 3;

        return -1;
    }
}
