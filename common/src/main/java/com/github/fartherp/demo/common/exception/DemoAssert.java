/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.demo.common.exception;

import com.github.fartherp.demo.common.enums.ErrorCodeEnum;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/11/27
 */
public class DemoAssert {

    public static <T> void notNull(T object, ErrorCodeEnum resultEnum) {
        if (object == null) {
            throw new AppException(resultEnum);
        }
    }

    public static <T> void notNull(T object, String result) {
        if (object == null) {
            throw new AppException(result);
        }
    }

    public static <T> void notEmpty(Collection<T> object, ErrorCodeEnum resultEnum) {
        if (CollectionUtils.isEmpty(object)) {
            throw new AppException(resultEnum);
        }
    }

    public static <T> void notEmpty(Collection<T> object, String result) {
        if (CollectionUtils.isEmpty(object)) {
            throw new AppException(result);
        }
    }

    public static <T> void isEmpty(Collection<T> object, ErrorCodeEnum resultEnum) {
        if (CollectionUtils.isNotEmpty(object)) {
            throw new AppException(resultEnum);
        }
    }

    public static <T> void isEmpty(Collection<T> object, String result) {
        if (CollectionUtils.isNotEmpty(object)) {
            throw new AppException(result);
        }
    }

    public static void test(Supplier<Boolean> supplier, ErrorCodeEnum resultEnum) {
        if (supplier.get()) {
            throw new AppException(resultEnum);
        }
    }

    public static void test(Supplier<Boolean> supplier, String result) {
        if (supplier.get()) {
            throw new AppException(result);
        }
    }
}
