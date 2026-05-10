package com.ds.dsfest.domain.notice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.ds.dsfest.domain.notice.constant.NoticeCategory;
import com.ds.dsfest.domain.notice.dto.NoticeCreateReqDto;
import com.ds.dsfest.domain.notice.dto.NoticeDetailResDto;
import com.ds.dsfest.domain.notice.dto.NoticeSearchResDto;
import com.ds.dsfest.domain.notice.dto.NoticeUpdateReqDto;
import com.ds.dsfest.domain.notice.dto.UrgentNoticeResDto;
import com.ds.dsfest.domain.notice.entity.Notice;
import com.ds.dsfest.domain.notice.exception.NoticeErrorCode;
import com.ds.dsfest.domain.notice.repository.NoticeRepository;
import com.ds.dsfest.global.exception.CustomException;
import com.ds.dsfest.global.infra.s3.S3Uploader;

@ExtendWith(MockitoExtension.class)
class NoticeServiceTest {

  @Mock private NoticeRepository noticeRepository;
  @Mock private S3Uploader s3Uploader;
  @InjectMocks private NoticeService noticeService;

  @Test
  @DisplayName("사용자 공지 상세 조회 시 조회수가 1 증가한다")
  void getNoticeDetailWithViewCount_incrementsViewCount() {
    Notice notice = Notice.create("제목", "내용", NoticeCategory.ETC, false);
    when(noticeRepository.findById(1L)).thenReturn(Optional.of(notice));

    noticeService.getNoticeDetailWithViewCount(1L);

    verify(noticeRepository).incrementViewCount(1L);
  }

  @Test
  @DisplayName("어드민 공지 상세 조회 시 조회수가 증가하지 않는다")
  void getNoticeDetail_doesNotIncrementViewCount() {
    Notice notice = Notice.create("제목", "내용", NoticeCategory.ETC, false);
    when(noticeRepository.findById(1L)).thenReturn(Optional.of(notice));

    noticeService.getNoticeDetail(1L);

    verify(noticeRepository, never()).incrementViewCount(any());
  }

  @Test
  @DisplayName("이미지 없이 공지를 생성하면 S3 업로드를 호출하지 않는다")
  void createNotice_withoutImages_noS3Upload() throws IOException {
    NoticeCreateReqDto req = new NoticeCreateReqDto("제목", NoticeCategory.ETC, false, "내용");
    Notice notice = Notice.create("제목", "내용", NoticeCategory.ETC, false);
    when(noticeRepository.save(any())).thenReturn(notice);

    noticeService.createNotice(req, null);

    verify(s3Uploader, never()).upload(any(), any());
  }

  @Test
  @DisplayName("이미지와 함께 공지를 생성하면 S3 업로드가 이미지 수만큼 호출된다")
  void createNotice_withImages_uploadsToS3() throws IOException {
    NoticeCreateReqDto req = new NoticeCreateReqDto("제목", NoticeCategory.ETC, false, "내용");
    Notice notice = Notice.create("제목", "내용", NoticeCategory.ETC, false);
    when(noticeRepository.save(any())).thenReturn(notice);
    when(s3Uploader.upload(any(), eq("notices")))
        .thenReturn("https://DSFest.s3.ap-northeast-2.amazonaws.com/notices/uuid_1.jpg")
        .thenReturn("https://DSFest.s3.ap-northeast-2.amazonaws.com/notices/uuid_2.jpg");

    List<MultipartFile> images =
        List.of(
            new MockMultipartFile("img1", "1.jpg", "image/jpeg", "data1".getBytes()),
            new MockMultipartFile("img2", "2.jpg", "image/jpeg", "data2".getBytes()));

    NoticeDetailResDto result = noticeService.createNotice(req, images);

    verify(s3Uploader, times(2)).upload(any(), eq("notices"));
    assertThat(result.imageUrls()).hasSize(2);
  }

  @Test
  @DisplayName("공지 수정 시 keepImageUrls에 없는 기존 이미지는 S3에서 삭제된다")
  void updateNotice_removesUnkeptImages() throws IOException {
    String oldUrl = "https://DSFest.s3.ap-northeast-2.amazonaws.com/notices/old.jpg";
    Notice notice = Notice.create("원본", "내용", NoticeCategory.ETC, false);
    notice.addImage(oldUrl, 0);
    when(noticeRepository.findById(1L)).thenReturn(Optional.of(notice));

    NoticeUpdateReqDto req =
        new NoticeUpdateReqDto("수정", NoticeCategory.EVENT, false, "수정 내용", List.of());

    noticeService.updateNotice(1L, req, null);

    verify(s3Uploader).delete(oldUrl);
  }

  @Test
  @DisplayName("공지 수정 시 keepImageUrls에 포함된 이미지는 S3에서 삭제하지 않는다")
  void updateNotice_keepsIncludedImages() throws IOException {
    String keepUrl = "https://DSFest.s3.ap-northeast-2.amazonaws.com/notices/keep.jpg";
    Notice notice = Notice.create("원본", "내용", NoticeCategory.ETC, false);
    notice.addImage(keepUrl, 0);
    when(noticeRepository.findById(1L)).thenReturn(Optional.of(notice));

    NoticeUpdateReqDto req =
        new NoticeUpdateReqDto("수정", NoticeCategory.EVENT, false, "수정 내용", List.of(keepUrl));

    noticeService.updateNotice(1L, req, null);

    verify(s3Uploader, never()).delete(keepUrl);
  }

  @Test
  @DisplayName("공지를 삭제하면 연결된 모든 이미지가 S3에서 삭제된다")
  void deleteNotice_deletesAllImagesFromS3() {
    String url1 = "https://DSFest.s3.ap-northeast-2.amazonaws.com/notices/img1.jpg";
    String url2 = "https://DSFest.s3.ap-northeast-2.amazonaws.com/notices/img2.jpg";
    Notice notice = Notice.create("제목", "내용", NoticeCategory.ETC, false);
    notice.addImage(url1, 0);
    notice.addImage(url2, 1);
    when(noticeRepository.findById(1L)).thenReturn(Optional.of(notice));

    noticeService.deleteNotice(1L);

    verify(s3Uploader).delete(url1);
    verify(s3Uploader).delete(url2);
    verify(noticeRepository).delete(notice);
  }

  @Test
  @DisplayName("존재하지 않는 공지 삭제 시 NOTICE_NOT_FOUND 예외가 발생한다")
  void deleteNotice_notFound_throwsException() {
    when(noticeRepository.findById(999L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> noticeService.deleteNotice(999L))
        .isInstanceOf(CustomException.class)
        .extracting(e -> ((CustomException) e).getErrorCode())
        .isEqualTo(NoticeErrorCode.NOTICE_NOT_FOUND);
  }

  @Test
  @DisplayName("검색 결과가 없으면 recommended에 조회수 상위 공지가 담긴다")
  void searchNotices_empty_returnsRecommended() {
    Notice recommended = Notice.create("자주 찾는 공지", "내용", NoticeCategory.ETC, false);
    when(noticeRepository.searchByKeyword("없는키워드")).thenReturn(List.of());
    when(noticeRepository.findTop5ByOrderByViewCountDescCreatedAtDesc())
        .thenReturn(List.of(recommended));

    NoticeSearchResDto result = noticeService.searchNotices("없는키워드");

    assertThat(result.results()).isEmpty();
    assertThat(result.recommended()).hasSize(1);
    assertThat(result.recommended().get(0).title()).isEqualTo("자주 찾는 공지");
  }

  @Test
  @DisplayName("검색 결과가 있으면 recommended는 빈 리스트다")
  void searchNotices_found_noRecommended() {
    Notice found = Notice.create("무대 안내", "내용", NoticeCategory.PERFORMANCE, false);
    when(noticeRepository.searchByKeyword("무대")).thenReturn(List.of(found));

    NoticeSearchResDto result = noticeService.searchNotices("무대");

    assertThat(result.results()).hasSize(1);
    assertThat(result.recommended()).isEmpty();
    verify(noticeRepository, never()).findTop5ByOrderByViewCountDescCreatedAtDesc();
  }

  @Test
  @DisplayName("긴급공지가 없으면 null을 반환한다")
  void getUrgentNotice_none_returnsNull() {
    when(noticeRepository.findTopByUrgentTrueOrderByCreatedAtDesc()).thenReturn(Optional.empty());

    UrgentNoticeResDto result = noticeService.getUrgentNotice();

    assertThat(result).isNull();
  }

  @Test
  @DisplayName("긴급공지가 있으면 id와 title을 반환한다")
  void getUrgentNotice_exists_returnsDto() {
    Notice urgent = Notice.create("긴급공지 제목", "내용", NoticeCategory.ETC, true);
    when(noticeRepository.findTopByUrgentTrueOrderByCreatedAtDesc())
        .thenReturn(Optional.of(urgent));

    UrgentNoticeResDto result = noticeService.getUrgentNotice();

    assertThat(result).isNotNull();
    assertThat(result.title()).isEqualTo("긴급공지 제목");
  }
}
