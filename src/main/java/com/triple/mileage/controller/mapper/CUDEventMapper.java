package com.triple.mileage.controller.mapper;

public interface CUDEventMapper<T>  {
    void add(T requestDto);
    void mod(T requestDto);
    void delete(T requestDto);
}
