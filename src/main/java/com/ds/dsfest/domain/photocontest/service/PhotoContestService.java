package com.ds.dsfest.domain.photocontest.service;

import com.ds.dsfest.domain.photocontest.constant.PhotoContestStatus;
import com.ds.dsfest.domain.photocontest.constant.PhotoTheme;
import com.ds.dsfest.domain.photocontest.dto.*;
import com.ds.dsfest.domain.photocontest.entity.PhotoContestSetting;
import com.ds.dsfest.domain.photocontest.entity.PhotoEntry;
import com.ds.dsfest.domain.photocontest.entity.PhotoParticipation;
import com.ds.dsfest.domain.photocontest.entity.PhotoVote;
import com.ds.dsfest.domain.photocontest.repository.*;
import com.ds.dsfest.global.exception.CustomException;
import com.ds.dsfest.global.exception.GlobalErrorCode;
import com.ds.dsfest.global.util.IdentityHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoContestService {

    private final PhotoContestSettingRepository photoContestSettingRepository;
    private final PhotoEntryRepository photoEntryRepository;
    private final PhotoVoteRepository photoVoteRepository;
    private final VerifiedStudentRepository verifiedStudentRepository;
    private final PhotoParticipationRepository photoParticipationRepository;

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
     * 사진 콘테스트 출품작 목록을 주제별로 분류하여 전체 조회합니다.
     */
    public PhotoListResDto getPhotoList() {
        List<PhotoEntry> allPhotos = photoEntryRepository.findAllByOrderByIdAsc();

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

    /**
     * 사진 콘테스트 투표를 검증하고 저장합니다.
     */
    @Transactional
    public void votePhotos(PhotoVoteReqDto reqDto) {
        String currentHash = IdentityHasher.hashIdentity(reqDto.studentId(), reqDto.studentName(), "재학생"); // 재학생 해시
        String leaveHash = IdentityHasher.hashIdentity(reqDto.studentId(), reqDto.studentName(), "휴학생"); // 휴학생 해시

        String voterKey = null;

        if (verifiedStudentRepository.existsById(currentHash)) { // 재학생 명단
            voterKey = currentHash;
        }
        else if (verifiedStudentRepository.existsById(leaveHash)) { // 휴학생 명단 ('학사' 키워드)
            voterKey = leaveHash;
        }
        else {
            throw new CustomException(GlobalErrorCode.NOT_FOUND);
        }

        /**
         * 중복 투표 검증 (이미 이 해시로 투표했는지 확인)
         */
        if (photoVoteRepository.existsByVoterKey(voterKey)) {
            throw new CustomException(GlobalErrorCode.BAD_REQUEST);
        }

        /**
         * 선택한 사진들 조회 및 검증
         */
        List<PhotoEntry> selectedPhotos = photoEntryRepository.findAllById(reqDto.photoEntryIds());
        if (selectedPhotos.size() != 3) {
            throw new CustomException(GlobalErrorCode.NOT_FOUND);
        }

        /**
         * 주제(Theme) 중복 체크
         */
        long themeCount = selectedPhotos.stream().map(PhotoEntry::getTheme).distinct().count();
        if (themeCount != 3) {
            throw new CustomException(GlobalErrorCode.BAD_REQUEST);
        }

        /**
         * 동시성 이슈 방어
         */
        try {
            photoParticipationRepository.save(new PhotoParticipation(voterKey));
            photoParticipationRepository.flush(); // 즉시 DB에 쏴서 충돌 여부 확인
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(GlobalErrorCode.BAD_REQUEST); // 늦게 들어온 요청은 여기서 튕김!
        }

        /**
         * 투표 내역 저장
         */
        String finalVoterKey = voterKey;
        List<PhotoVote> votes = selectedPhotos.stream()
            .map(photo -> new PhotoVote(photo, finalVoterKey))
            .toList();

        photoVoteRepository.saveAll(votes);
    }

    /**
     * 총 학생용 투표 결과 집계 (테마별 분류)
     */
    @Transactional(readOnly = true)
    public Map<String, List<PhotoRankResDto>> getVoteResults() {
        List<Object[]> results = photoVoteRepository.countVotesPerPhoto();

        List<PhotoRankResDto> allRanks = results.stream().map(result -> {
            Long photoId = (Long) result[0];
            Long count = (Long) result[1];
            PhotoEntry photo = photoEntryRepository.findById(photoId).orElseThrow();

            return new PhotoRankResDto(
                photo.getId(),
                photo.getTitle(),
                photo.getAuthorName(),
                photo.getTheme().name(),
                count,
                photo.getImageUrl()
            );
        }).toList();

        return allRanks.stream()
            .collect(Collectors.groupingBy(PhotoRankResDto::theme));
    }
}
