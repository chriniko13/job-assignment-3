package com.yoti.assignment.sdkbackendtest.mapper.boundary;

import com.yoti.assignment.sdkbackendtest.mapper.control.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapperHandler {

    private final List<Mapper<?, ?>> mappers;

    @Autowired
    public MapperHandler(List<Mapper<?, ?>> mappers) {
        this.mappers = mappers;
    }

    @SuppressWarnings("unchecked")
    public <S, D> Mapper<S, D> getMapping(Class<S> source, Class<D> destination) {
        for (Mapper<?, ?> mapper : mappers) {
            if (mapper.canApply(source, destination)) {
                return (Mapper<S, D>) mapper;
            }
        }
        throw new IllegalStateException();
    }

}
