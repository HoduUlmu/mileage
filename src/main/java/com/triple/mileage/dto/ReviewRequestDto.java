package com.triple.mileage.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class ReviewRequestDto {
    private final UUID reviewId;
    private String content;
    private List<UUID> attachedPhotoIds;
    private final UUID userId;
    private final UUID placeId;


    @Builder
    public ReviewRequestDto(UUID reviewId, String content, List<UUID> attachedPhotoIds, UUID userId, UUID placeId) {
        this.reviewId = reviewId;
        this.content = content;
        this.attachedPhotoIds = attachedPhotoIds;
        this.userId = userId;
        this.placeId = placeId;
    }

    public void nullCheck() {
        if (this.content == null) {
            this.content = "";
        }
        if (attachedPhotoIds == null) {
            this.attachedPhotoIds = new ArrayList<>();
        }
    }
}
