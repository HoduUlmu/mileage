package com.triple.mileage.dto;

import com.triple.mileage.web.constant.ActionEnum;
import com.triple.mileage.web.constant.TypeEnum;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class EventRequestDto {
    private TypeEnum type;
    private ActionEnum action;
    private UUID reviewId;
    private String content;
    private Set<UUID> attachedPhotoIds;
    private UUID userId;
    private UUID placeId;

    public void nullCheck() {
        if (this.content == null) {
            this.content = "";
        }
        if (attachedPhotoIds == null) {
            this.attachedPhotoIds = new HashSet<>();
        }
    }
}
