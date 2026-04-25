package com.ds.dsfest.domain.photocontest.service;

import com.ds.dsfest.domain.photocontest.constant.PhotoContestStatus;
import com.ds.dsfest.domain.photocontest.constant.PhotoTheme;
import com.ds.dsfest.domain.photocontest.dto.PhotoContestStatusResDto;
import com.ds.dsfest.domain.photocontest.dto.PhotoDetailResDto;
import com.ds.dsfest.domain.photocontest.dto.PhotoListResDto;
import com.ds.dsfest.domain.photocontest.entity.PhotoContestSetting;
import com.ds.dsfest.domain.photocontest.entity.PhotoEntry;
import com.ds.dsfest.domain.photocontest.repository.PhotoContestSettingRepository;
import com.ds.dsfest.domain.photocontest.repository.PhotoEntryRepository;
import com.ds.dsfest.global.exception.CustomException;
import com.ds.dsfest.global.exception.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
     * 특정 출품작의 상세 정보를 조회합니다.
     * * @param photoEntryId 조회할 출품작 ID
     * @return 조회된 출품작 데이터를 담은 DTO
     * @throws CustomException 해당 ID의 출품작이 존재하지 않을 경우 NOT_FOUND 예외 발생
     */
    public PhotoDetailResDto getPhotoDetail(Long photoEntryId) {
        com.ds.dsfest.domain.photocontest.entity.PhotoEntry photoEntry = photoEntryRepository.findById(photoEntryId)
            .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));

        return PhotoDetailResDto.from(photoEntry);
    }

    /**
     * 사진 콘테스트 출품작 목록을 주제별로 분류하여 전체 조회합니다. (A.7.2.1)
     */
    public PhotoListResDto getPhotoList() {
        List<PhotoEntry> allPhotos = photoEntryRepository.findAll();

        List<PhotoListResDto.PhotoSummaryDto> youthPhotos = allPhotos.stream()
            .filter(photo -> photo.getTheme() == PhotoTheme.YOUTH)
            .map(PhotoListResDto.PhotoSummaryDto::from)
            .toList();

        List<PhotoListResDto.PhotoSummaryDto> festivalPhotos = allPhotos.stream()
            .filter(photo -> photo.getTheme() == PhotoTheme.FESTIVAL)
            .map(PhotoListResDto.PhotoSummaryDto::from)
            .toList();

        List<PhotoListResDto.PhotoSummaryDto> dressCodePhotos = allPhotos.stream()
            .filter(photo -> photo.getTheme() == PhotoTheme.DRESS_CODE)
            .map(PhotoListResDto.PhotoSummaryDto::from)
            .toList();

        return new PhotoListResDto(youthPhotos, festivalPhotos, dressCodePhotos);
    }
}
