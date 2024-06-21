package com.managementidea.bus.utils;

import org.springframework.beans.BeanUtils;

public class Helper {

    public static <S,D> D copyProperties(S source, D destination) {
        BeanUtils.copyProperties(source, destination);
        return destination;
    }
}
