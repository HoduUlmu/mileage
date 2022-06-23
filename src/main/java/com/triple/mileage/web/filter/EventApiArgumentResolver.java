package com.triple.mileage.web.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple.mileage.dto.EventRequestDto;
import com.triple.mileage.web.annotation.EventsApi;
import org.springframework.core.MethodParameter;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class EventApiArgumentResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper om = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(EventsApi.class) != null
                && parameter.getParameterType().equals(EventRequestDto.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        assert request != null;
        InputStream inputStream = request.getInputStream();
        String read = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        return om.readValue(read, EventRequestDto.class);
    }
}
