package com.triple.mileage.controller.mapper;


import com.triple.mileage.dto.ReviewRequestDto;
import com.triple.mileage.exception.custom.business.InternalServerException;
import com.triple.mileage.web.constant.ActionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.triple.mileage.web.constant.ActionEnum.*;

@Component
public class ReviewActionMapper implements ActionMapper<ReviewRequestDto> {

    private final ConcurrentMap<ActionEnum, Method> mapper = new ConcurrentHashMap<>();
    private final CUDEventMapper<?> reviewEventMapper;

    public ReviewActionMapper(@Autowired ReviewServiceMapper reviewEventMapper) {
        this.reviewEventMapper = reviewEventMapper;
    }

    @PostConstruct
    private void init() {
        try {
            mapper.put(ADD, ReviewServiceMapper.class.getDeclaredMethod("add", ReviewRequestDto.class));
            mapper.put(MOD, ReviewServiceMapper.class.getDeclaredMethod("mod", ReviewRequestDto.class));
            mapper.put(DELETE, ReviewServiceMapper.class.getDeclaredMethod("delete", ReviewRequestDto.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void action(ActionEnum action, @Valid ReviewRequestDto requestDto) {
        Method method = mapper.get(action);
        try {
            method.invoke(reviewEventMapper, requestDto);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new InternalServerException(e);
        }
    }

}
