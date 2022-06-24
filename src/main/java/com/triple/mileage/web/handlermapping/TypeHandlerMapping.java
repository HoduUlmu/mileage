package com.triple.mileage.web.handlermapping;

import com.triple.mileage.annotation.Type;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

public class TypeHandlerMapping extends RequestMappingHandlerMapping {
    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        return from(AnnotationUtils.findAnnotation(handlerType, Type.class));
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        return from(AnnotationUtils.findAnnotation(method, Type.class));
    }

    private TypeRequestCondition from(Type type) {
        return type == null ? null : new TypeRequestCondition(type.value());
    }
}
