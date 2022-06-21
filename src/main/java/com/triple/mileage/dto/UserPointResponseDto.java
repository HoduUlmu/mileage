package com.triple.mileage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UserPointResponseDto {
    private UUID userId;
    private Long point;

    public UserPointResponseDto(UUID userId, Long point) {
        this.userId = userId;
        this.point = point;
    }
}
