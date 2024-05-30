package com.example.spring_boot_demo.Utility;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class GenericBeanMapper {
    public static <S, T> T map(S source, Class<T> targetClass) {
        if (source == null)
            return null;
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <S, T> List<T> mapList(List<S> sourceList, Class<T> targetClass) {
        return sourceList.stream()
                .map(source -> map(source, targetClass))
                .collect(Collectors.toList());
    }
}
