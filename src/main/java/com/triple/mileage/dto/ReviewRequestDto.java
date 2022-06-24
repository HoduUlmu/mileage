package com.triple.mileage.dto;

import com.triple.mileage.constant.ActionEnum;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class ReviewRequestDto {

    @NotNull(message = "액션이 필요합니다")
    private ActionEnum action;

    @NotNull(message = "리뷰 ID가 필요합니다")
    private UUID reviewId;

    private String content;

    private List<UUID> attachedPhotoIds;

    @NotNull(message = "유저 ID가 필요합니다")
    private UUID userId;

    @NotNull(message = "장소 ID가 필요합니다")
    private UUID placeId;

    public void nullCheck() {
        if (this.content == null) {
            this.content = "";
        }
        if (attachedPhotoIds == null) {
            this.attachedPhotoIds = new ArrayList<>();
        }
    }
}
