package com.ds.dsfest.domain.foodtruck.service;

import com.ds.dsfest.domain.booth.exception.BoothErrorCode;
import com.ds.dsfest.domain.foodtruck.dto.FoodTruckLikeResDto;
import com.ds.dsfest.domain.foodtruck.dto.FoodTruckListResDto;
import com.ds.dsfest.domain.foodtruck.entity.FoodTruck;
import com.ds.dsfest.domain.foodtruck.entity.FoodTruckLike;
import com.ds.dsfest.domain.foodtruck.entity.FoodTruckOperatingDay;
import com.ds.dsfest.domain.foodtruck.mapper.FoodTruckMapper;
import com.ds.dsfest.domain.foodtruck.repository.FoodTruckLikeRepository;
import com.ds.dsfest.domain.foodtruck.repository.FoodTruckRepository;
import com.ds.dsfest.domain.user.entity.GuestUser;
import com.ds.dsfest.domain.user.exception.UserErrorCode;
import com.ds.dsfest.domain.user.repository.GuestUserRepository;
import com.ds.dsfest.global.exception.CustomException;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * 푸드트럭 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodTruckService {

    private final FoodTruckRepository foodTruckRepository;
    private final FoodTruckLikeRepository foodTruckLikeRepository;
    private final GuestUserRepository guestUserRepository;
    private final FoodTruckMapper foodTruckMapper;
    private final Clock clock;

    @Value("${festival.start-date}")
    private String festivalStartDateStr;

    /**
     * 푸드트럭 목록을 조회하며, 실제 DB에 저장된 좋아요 개수를 합산하여 반환합니다.
     *
     * @return 좋아요 개수가 포함된 푸드트럭 리스트 DTO 목록
     */
    public List<FoodTruckListResDto> getFoodTruckList() {
        List<FoodTruck> foodTrucks = foodTruckRepository.findAllWithOperatingDays();

        LocalDateTime now = LocalDateTime.now(clock);
        LocalDate today = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        int currentFestivalDay = getFestivalDayFromDate(today);

        List<FoodTruckListResDto> resultList = foodTrucks.stream()
            .map(truck -> {
                Optional<FoodTruckOperatingDay> todaySchedule = truck.getOperatingDays().stream()
                    .filter(schedule -> schedule.getFestivalDay() == currentFestivalDay)
                    .findFirst();

                boolean isOpen = false;
                String operatingDaysString = "운영 정보 없음";

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

                    isOpen = !currentTime.isBefore(start) && currentTime.isBefore(end);
                }


                int likeCount = (int) foodTruckLikeRepository.countByFoodTruck(truck); // 해당 트럭의 좋아요 개수 조회

                return foodTruckMapper.toFoodTruckListResDto(truck, operatingDaysString, likeCount, isOpen);
            })
            .collect(Collectors.toList());

        Collections.shuffle(resultList);

        return resultList;
    }

    /**
     * 특정 푸드트럭에 대한 좋아요 상태를 토글합니다.
     *
     * @return 좋아요 후의 최종 상태 (true: 추가됨, false: 취소됨)
     */
    @Transactional
    public FoodTruckLikeResDto toggleFoodTruckLike(Long foodTruckId, UUID guestUuid) {
        /**
         * 1. 푸드트럭 존재 여부 확인
         */
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId)
            .orElseThrow(() -> new CustomException(BoothErrorCode.BOOTH_NOT_FOUND));

        /**
         * 2. 사용자(GuestUser) 존재 여부 확인
         */
        GuestUser guestUser = guestUserRepository.findById(guestUuid)
            .orElseThrow(() -> new CustomException(UserErrorCode.GUEST_NOT_FOUND));

        /**
         * 3. 좋아요 토글 로직
         */
        Optional<FoodTruckLike> existingLike = foodTruckLikeRepository.findByGuestUserAndFoodTruck(guestUser, foodTruck);

        if (existingLike.isPresent()) {
            foodTruckLikeRepository.delete(existingLike.get());
            return new FoodTruckLikeResDto(false);
        } else {
            FoodTruckLike newLike = FoodTruckLike.builder()
                .guestUser(guestUser)
                .foodTruck(foodTruck)
                .build();
            foodTruckLikeRepository.save(newLike);
            return new FoodTruckLikeResDto(true);
        }
    }

    /**
     * 날짜를 기반으로 축제 일차를 계산합니다.
     *
     * @param date 기준 날짜
     * @return 축제 일차 (1~3), 기간 외일 경우 -1
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
