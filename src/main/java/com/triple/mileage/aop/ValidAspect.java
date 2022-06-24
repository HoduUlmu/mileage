package com.triple.mileage.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

@Aspect
@Component
public class ValidAspect {

    @Pointcut("execution(* com.triple.mileage.controller.EventController.events(..))")
    private void events() {}

    @Around(value = "events()")
    public Object doValidate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                if (bindingResult.hasErrors())
                    throw new BindException(bindingResult);
            }
        }
        return joinPoint.proceed();
    }
}
