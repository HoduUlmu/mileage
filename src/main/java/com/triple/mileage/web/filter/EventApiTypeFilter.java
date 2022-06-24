package com.triple.mileage.web.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple.mileage.dto.EventRequestDto;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class EventApiTypeFilter extends OncePerRequestFilter {

    private final ObjectMapper om = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InputStream inputStream = request.getInputStream();
        String read = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        EventRequestDto requestDto = om.readValue(read, EventRequestDto.class);
        request.setAttribute("type", requestDto.getType());
        filterChain.doFilter(request, response);
    }
}
