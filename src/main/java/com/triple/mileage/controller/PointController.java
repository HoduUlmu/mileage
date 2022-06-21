package com.triple.mileage.controller;

import com.triple.mileage.dto.UserPointResponseDto;
import com.triple.mileage.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/points")
    public ResponseEntity<UserPointResponseDto> getUserPoint(@RequestParam UUID userId) {
        return ResponseEntity.ok(new UserPointResponseDto(userId, pointService.getUserPoint(userId)));
    }
}
