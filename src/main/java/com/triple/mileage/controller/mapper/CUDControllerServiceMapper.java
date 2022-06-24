package com.triple.mileage.controller.mapper;

public interface CUDControllerServiceMapper<T>  {
    void add(T requestDto);
    void mod(T requestDto);
    void delete(T requestDto);
}
