package com.ds.dsfest.domain.photocontest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 사진 콘테스트 출품작 상세 정보 조회를 위한 응답 DTO
 * * @param id 사진 고유 ID
 * @param title 사진 제목
 * @param authorName 출품자 이름
 * @param description 사진 설명
 * @param imageUrl 사진 이미지 URL
 */
@Schema(description = "사진 상세 정보 응답 DTO")
public record PhotoDetailResDto(
    @Schema(description = "사진 고유 ID", example = "1")
    Long id,

    @Schema(description = "사진 제목", example = "우리들의 빛나는 청춘")
    String title,

    @Schema(description = "출품자 이름", example = "김덕우")
    String authorName,

    @Schema(description = "사진 상세 설명", example = "축제 첫날 동기들과 함께 찍은 사진입니다.")
    String description,

    @Schema(description = "이미지 URL", example = "https://example.com/photo1.jpg")
    String imageUrl
) {
    public static PhotoDetailResDto from(com.ds.dsfest.domain.photocontest.entity.PhotoEntry entity) {
        return new PhotoDetailResDto(
            entity.getId(),
            entity.getTitle(),
            entity.getAuthorName(),
            entity.getDescription(),
            entity.getImageUrl()
        );
    }
}
