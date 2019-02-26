package com.yoti.assignment.sdkbackendtest.mapper.control;

public interface Mapper<S, D> {

    D apply(S source);

    boolean canApply(Class<?> source, Class<?> destination);
}
