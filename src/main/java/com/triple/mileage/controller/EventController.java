package com.triple.mileage.controller;

import com.triple.mileage.dto.EventRequestDto;
import com.triple.mileage.web.annotation.EventsApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class EventController {

    @PostMapping("/events")
    public void events(HttpServletRequest request, @EventsApi EventRequestDto eventRequestDto) {

    }
}

