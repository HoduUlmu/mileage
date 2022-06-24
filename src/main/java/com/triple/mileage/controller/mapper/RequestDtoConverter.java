package com.triple.mileage.controller.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple.mileage.dto.ReviewRequestDto;
import com.triple.mileage.exception.custom.business.ConvertException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class RequestDtoConverter {

    private final ObjectMapper om;

    public ReviewRequestDto toReviewDto(HttpServletRequest request) {
        try {
            ServletInputStream inputStream = request.getInputStream();
            String data = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            return om.readValue(data, ReviewRequestDto.class);
        } catch (IOException e) {
            throw new ConvertException(e);
        }
    }
}
