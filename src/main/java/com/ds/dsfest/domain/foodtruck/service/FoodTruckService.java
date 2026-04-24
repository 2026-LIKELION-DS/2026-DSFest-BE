package com.ds.dsfest.domain.foodtruck.service;

import com.ds.dsfest.domain.foodtruck.dto.FoodTruckListResDto;
import com.ds.dsfest.domain.foodtruck.entity.FoodTruck;
import com.ds.dsfest.domain.foodtruck.entity.FoodTruckOperatingDay;
import com.ds.dsfest.domain.foodtruck.mapper.FoodTruckMapper;
import com.ds.dsfest.domain.foodtruck.repository.FoodTruckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 푸드트럭 도메인의 비즈니스 로직을 담당하는 서비스 클래스입니다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodTruckService {

    private final FoodTruckRepository foodTruckRepository;
    private final FoodTruckMapper foodTruckMapper;
    private final Clock clock;

    /**
     * application.yml의 festival.start-date 값을 가져옵니다.
     */
    @Value("${festival.start-date}")
    private String festivalStartDateStr;

    /**
     * 현재 서버 시간과 DB의 운영 일차 정보를 비교하여 푸드트럭 목록을 조회합니다.
     */
    public List<FoodTruckListResDto> getFoodTruckList() {
        List<FoodTruck> foodTrucks = foodTruckRepository.findAllWithOperatingDays();

        LocalDateTime now = LocalDateTime.now(clock);
        LocalDate today = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        int currentFestivalDay = getFestivalDayFromDate(today); // 오늘 축제 몇 일차인지 계산

        List<FoodTruckListResDto> resultList = foodTrucks.stream()
            .map(truck -> {
                /**
                 * 엔티티의 festivalDay 필드를 사용하여 오늘 일정 필터링
                 */
                Optional<FoodTruckOperatingDay> todaySchedule = truck.getOperatingDays().stream()
                    .filter(schedule -> schedule.getFestivalDay() == currentFestivalDay)
                    .findFirst();

                boolean isOpen = false;
                String operatingDaysString = "운영 정보 없음";

                if (todaySchedule.isPresent()) {
                    FoodTruckOperatingDay schedule = todaySchedule.get();
                    LocalTime start = schedule.getStartTime();
                    LocalTime end = schedule.getEndTime();

                    /**
                     * 피그마 양식에 맞게 문자열 조립
                     */
                    String dayOfWeek = today.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
                    operatingDaysString = String.format("%d일(%s) %s - %s",
                        today.getDayOfMonth(),
                        dayOfWeek,
                        start.format(timeFormatter),
                        end.format(timeFormatter));

                    isOpen = !currentTime.isBefore(start) && currentTime.isBefore(end); // 영업 여부 판별
                }

                Integer dummyLikeCount = 999;

                return foodTruckMapper.toFoodTruckListResDto(truck, operatingDaysString, dummyLikeCount, isOpen);
            })
            .collect(Collectors.toList());

        Collections.shuffle(resultList);

        return resultList;
    }

    /**
     * 날짜를 기반으로 축제 일차를 계산합니다.
     */
    private int getFestivalDayFromDate(LocalDate date) {
        LocalDate startDate = LocalDate.parse(festivalStartDateStr);
        long diff = ChronoUnit.DAYS.between(startDate, date);
        int festivalDay = (int) diff + 1;

        if (festivalDay >= 1 && festivalDay <= 3) {
            return festivalDay;
        }
        return -1;
    }
}
