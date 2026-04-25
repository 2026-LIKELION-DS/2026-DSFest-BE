package com.ds.dsfest.domain.photocontest.service;

import com.ds.dsfest.domain.photocontest.constant.PhotoContestStatus;
import com.ds.dsfest.domain.photocontest.dto.PhotoContestStatusResDto;
import com.ds.dsfest.domain.photocontest.dto.PhotoDetailResDto;
import com.ds.dsfest.domain.photocontest.entity.PhotoContestSetting;
import com.ds.dsfest.domain.photocontest.repository.PhotoContestSettingRepository;
import com.ds.dsfest.domain.photocontest.repository.PhotoEntryRepository;
import com.ds.dsfest.global.exception.CustomException;
import com.ds.dsfest.global.exception.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoContestService {

    private final PhotoContestSettingRepository photoContestSettingRepository;
    private final PhotoEntryRepository photoEntryRepository;

    /**
     * 사진 콘테스트의 현재 상태 및 마감 시간을 반환합니다.
     */
    public PhotoContestStatusResDto getContestStatus() {

        PhotoContestSetting setting = photoContestSettingRepository.findById(1L)
            .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));

        if (setting.getStartTime() == null || setting.getEndTime() == null) {
            throw new CustomException(GlobalErrorCode.INVALID_INPUT);
        }
        if (!setting.getStartTime().isBefore(setting.getEndTime())) {
            throw new CustomException(GlobalErrorCode.INVALID_INPUT);
        }

        LocalDateTime now = LocalDateTime.now();
        PhotoContestStatus derivedStatus;
        if (now.isBefore(setting.getStartTime())) {
            derivedStatus = PhotoContestStatus.ACCEPTING;
        } else if (now.isBefore(setting.getEndTime())) {
            derivedStatus = PhotoContestStatus.VOTING;
        } else {
            derivedStatus = PhotoContestStatus.ENDED;
        }

        return new PhotoContestStatusResDto(
            derivedStatus.name(),
            setting.getStartTime(),
            setting.getEndTime()
        );
    }

    /**
     * 사진 상세 정보를 조회합니다.
     */
    public PhotoDetailResDto getPhotoDetail(Long photoEntryId) {
        com.ds.dsfest.domain.photocontest.entity.PhotoEntry photoEntry = photoEntryRepository.findById(photoEntryId)
            .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));

        return PhotoDetailResDto.from(photoEntry);
    }
}
