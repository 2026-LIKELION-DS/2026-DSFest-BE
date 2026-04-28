package com.ds.dsfest.domain.notice.service;

import com.ds.dsfest.domain.notice.constant.NoticeCategory;
import com.ds.dsfest.domain.notice.dto.NoticeCreateReqDto;
import com.ds.dsfest.domain.notice.dto.NoticeDetailResDto;
import com.ds.dsfest.domain.notice.dto.NoticeListItemResDto;
import com.ds.dsfest.domain.notice.dto.NoticeSearchResDto;
import com.ds.dsfest.domain.notice.dto.NoticeUpdateReqDto;
import com.ds.dsfest.domain.notice.dto.UrgentNoticeResDto;
import com.ds.dsfest.domain.notice.entity.Notice;
import com.ds.dsfest.domain.notice.exception.NoticeErrorCode;
import com.ds.dsfest.domain.notice.repository.NoticeRepository;
import com.ds.dsfest.global.exception.CustomException;
import com.ds.dsfest.global.infra.s3.S3Uploader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

  private static final String S3_DIR = "notices";

  private final NoticeRepository noticeRepository;
  private final S3Uploader s3Uploader;

  public NoticeDetailResDto createNotice(NoticeCreateReqDto req, List<MultipartFile> images)
      throws IOException {
    Notice notice = Notice.create(req.title(), req.content(), req.category(), req.urgent());
    noticeRepository.save(notice);

    if (images != null) {
      uploadAndAttachImages(notice, images, 0);
    }

    return NoticeDetailResDto.from(notice);
  }

  @Transactional(readOnly = true)
  public List<NoticeListItemResDto> getNoticeList() {
    return noticeRepository.findAllByOrderByCreatedAtDesc().stream()
        .map(NoticeListItemResDto::from)
        .toList();
  }

  @Transactional(readOnly = true)
  public List<NoticeListItemResDto> getNoticeListByCategory(NoticeCategory category) {
    return noticeRepository.findAllByCategoryOrderByCreatedAtDesc(category).stream()
        .map(NoticeListItemResDto::from)
        .toList();
  }

  @Transactional(readOnly = true)
  public NoticeDetailResDto getNoticeDetail(Long noticeId) {
    Notice notice = findNoticeOrThrow(noticeId);
    return NoticeDetailResDto.from(notice);
  }

  public NoticeDetailResDto getNoticeDetailWithViewCount(Long noticeId) {
    noticeRepository.incrementViewCount(noticeId);
    Notice notice = findNoticeOrThrow(noticeId);
    return NoticeDetailResDto.from(notice);
  }

  public NoticeDetailResDto updateNotice(
      Long noticeId, NoticeUpdateReqDto req, List<MultipartFile> newImages) throws IOException {
    Notice notice = findNoticeOrThrow(noticeId);
    notice.update(req.title(), req.content(), req.category(), req.urgent());

    List<String> keepUrls = req.keepImageUrls() != null ? req.keepImageUrls() : List.of();
    replaceImages(notice, keepUrls, newImages);

    noticeRepository.flush();
    return NoticeDetailResDto.from(notice);
  }

  public void deleteNotice(Long noticeId) {
    Notice notice = findNoticeOrThrow(noticeId);
    notice.getImages().forEach(image -> s3Uploader.delete(image.getImageUrl()));
    noticeRepository.delete(notice);
  }

  public void clearAllUrgent() {
    noticeRepository.clearAllUrgent();
  }

  @Transactional(readOnly = true)
  public NoticeSearchResDto searchNotices(String keyword) {
    List<NoticeListItemResDto> results =
        noticeRepository.searchByKeyword(keyword).stream().map(NoticeListItemResDto::from).toList();

    List<NoticeListItemResDto> recommended =
        results.isEmpty()
            ? noticeRepository.findTop4ByOrderByViewCountDescCreatedAtDesc().stream()
                .map(NoticeListItemResDto::from)
                .toList()
            : List.of();

    return new NoticeSearchResDto(results, recommended);
  }

  @Transactional(readOnly = true)
  public UrgentNoticeResDto getUrgentNotice() {
    return noticeRepository
        .findTopByUrgentTrueOrderByCreatedAtDesc()
        .map(UrgentNoticeResDto::from)
        .orElse(null);
  }

  private void replaceImages(Notice notice, List<String> keepUrls, List<MultipartFile> newImages)
      throws IOException {
    Set<String> existingUrls =
        notice.getImages().stream().map(image -> image.getImageUrl()).collect(Collectors.toSet());

    List<String> validatedKeepUrls = keepUrls.stream().filter(existingUrls::contains).toList();

    List<String> urlsToDelete =
        existingUrls.stream().filter(url -> !validatedKeepUrls.contains(url)).toList();

    notice.clearImages();

    List<String> allUrls = new ArrayList<>(validatedKeepUrls);
    if (newImages != null) {
      for (MultipartFile file : newImages) {
        allUrls.add(s3Uploader.upload(file, S3_DIR));
      }
    }

    for (int i = 0; i < allUrls.size(); i++) {
      notice.addImage(allUrls.get(i), i);
    }

    urlsToDelete.forEach(s3Uploader::delete);
  }

  private void uploadAndAttachImages(Notice notice, List<MultipartFile> images, int startOrder)
      throws IOException {
    for (int i = 0; i < images.size(); i++) {
      String url = s3Uploader.upload(images.get(i), S3_DIR);
      notice.addImage(url, startOrder + i);
    }
  }

  private Notice findNoticeOrThrow(Long noticeId) {
    return noticeRepository
        .findById(noticeId)
        .orElseThrow(() -> new CustomException(NoticeErrorCode.NOTICE_NOT_FOUND));
  }

   @Transactional(readOnly = true)
   public List<NoticeListItemResDto> getFrequentNoticeList() {
      return noticeRepository.findTop4ByOrderByViewCountDescCreatedAtDesc().stream()
          .map(NoticeListItemResDto::from)
          .toList();
  }
}
