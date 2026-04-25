package com.ds.dsfest.domain.photocontest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "사진 목록 무한 스크롤 응답 DTO")
public record PhotoListResDto(
    @Schema(description = "1. 자신의 청춘을 가장 잘 담은 사진 목록 (6개)")
    List<PhotoSummaryDto> youthPhotos,

    @Schema(description = "2. 축제 현장을 가장 잘 담은 사진 목록 (6개)")
    List<PhotoSummaryDto> festivalPhotos,

    @Schema(description = "3. 드레스코드를 가장 잘 살려 입은 사진 목록 (6개)")
    List<PhotoSummaryDto> dressCodePhotos
) {
    @Schema(description = "사진 목록 요약 정보")
    public record PhotoSummaryDto(
        @Schema(description = "사진 고유 ID", example = "1")
        Long photoEntryId,

        @Schema(description = "사진 제목", example = "우리들의 빛나는 청춘")
        String title,

        @Schema(description = "출품자 이름", example = "김덕우")
        String authorName,

        @Schema(description = "이미지 URL (썸네일)", example = "https://example.com/photo1.jpg")
        String imageUrl
    ) {
        public static PhotoSummaryDto from(com.ds.dsfest.domain.photocontest.entity.PhotoEntry entity) {
            return new PhotoSummaryDto(
                entity.getId(),
                entity.getTitle(),
                entity.getAuthorName(),
                entity.getImageUrl()
            );
        }
    }
}
