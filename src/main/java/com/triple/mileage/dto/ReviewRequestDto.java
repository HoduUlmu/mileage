package com.triple.mileage.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class ReviewRequestDto {
    private UUID reviewId;
    private String content;
    private List<UUID> attachedPhotoIds;
    private UUID userId;
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
