package com.triple.mileage.annotation;

import com.triple.mileage.constant.TypeEnum;

import java.lang.annotation.*;

// type 따라 핸들러 매칭
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Type {
    TypeEnum value();
}
