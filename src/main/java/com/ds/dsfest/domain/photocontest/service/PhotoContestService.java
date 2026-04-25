package com.ds.dsfest.domain.photocontest.service;

import com.ds.dsfest.domain.photocontest.dto.PhotoContestStatusResDto;
import com.ds.dsfest.domain.photocontest.entity.PhotoContestSetting;
import com.ds.dsfest.domain.photocontest.repository.PhotoContestSettingRepository;
import com.ds.dsfest.global.exception.CustomException;
import com.ds.dsfest.global.exception.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoContestService {

    private final PhotoContestSettingRepository photoContestSettingRepository;

    /**
     * 사진 콘테스트의 현재 상태 및 마감 시간을 반환합니다.
     */
    public PhotoContestStatusResDto getContestStatus() {

        PhotoContestSetting setting = photoContestSettingRepository.findById(1L)
            .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));

        return new PhotoContestStatusResDto(
            setting.getStatus().name(),
            setting.getStartTime(),
            setting.getEndTime()
        );
    }
}
