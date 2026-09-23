package com.shinkwang.devjob.util;

import com.shinkwang.devjob.exception.BusinessException;
import com.shinkwang.devjob.exception.ErrorCode;
import org.springframework.data.domain.Sort;

import java.util.Set;

public class SortValidator {

    private SortValidator() {}

    public static void validate(Sort sort, Set<String> allowed) {
        for (Sort.Order order : sort) {
            if (!allowed.contains(order.getProperty())) {
                throw new BusinessException(ErrorCode.INVALID_SORT_PROPERTY);
            }
        }
    }
}
