/**
 *    Copyright (c) 2018-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.fartherp.demo.common.exception;

import com.github.fartherp.demo.common.enums.ErrorCodeEnum;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/11/27
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
