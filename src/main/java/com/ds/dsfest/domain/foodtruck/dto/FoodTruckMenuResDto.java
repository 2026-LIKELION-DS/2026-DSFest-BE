package com.ds.dsfest.domain.foodtruck.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "푸드트럭 단일 메뉴 정보")
public record FoodTruckMenuResDto(
    @Schema(description = "메뉴 이름", example = "크래미 김밥")
    String menuName,

    @Schema(description = "메뉴 가격", example = "8000")
    Integer price,

    @Schema(description = "비건 메뉴 여부", example = "true")
    boolean isVegan
) {
}
