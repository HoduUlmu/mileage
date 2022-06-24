package com.triple.mileage.controller.mapper.action;


import com.triple.mileage.controller.mapper.CUDControllerServiceMapper;
import com.triple.mileage.controller.ReviewController;
import com.triple.mileage.dto.ReviewRequestDto;
import com.triple.mileage.exception.custom.business.InternalServerException;
import com.triple.mileage.constant.ActionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.triple.mileage.constant.ActionEnum.*;

// event 컨트롤러에서 보내준 action 을 가지고 적절한 서비스 컨트롤러 호출
@Component
public class ReviewActionMapper implements ActionMapper<ReviewRequestDto> {

    private final ConcurrentMap<ActionEnum, Method> mapper = new ConcurrentHashMap<>();
    private final CUDControllerServiceMapper<?> reviewEventMapper;

    public ReviewActionMapper(@Autowired ReviewController reviewEventMapper) {
        this.reviewEventMapper = reviewEventMapper;
    }

    @PostConstruct
    private void init() {
        try {
            mapper.put(ADD, ReviewController.class.getDeclaredMethod("add", ReviewRequestDto.class));
            mapper.put(MOD, ReviewController.class.getDeclaredMethod("mod", ReviewRequestDto.class));
            mapper.put(DELETE, ReviewController.class.getDeclaredMethod("delete", ReviewRequestDto.class));
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
