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
package com.github.fartherp.demo.base;

import com.github.fartherp.demo.common.base.JsonResp;
import com.github.fartherp.demo.common.enums.ErrorCodeEnum;
import com.github.fartherp.demo.common.exception.AppException;
import com.github.fartherp.demo.common.util.PropertyConfigurer;
import com.github.fartherp.framework.exception.SystemException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Spring MVC 配置
 * @author CK
 * @date 2018/4/28
 */
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    //统一异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add((request, response, handler, e) -> {
            if (response.isCommitted()) {
                return null;
            }
            Method method = ((HandlerMethod) handler).getMethod();
            Class clazz = method.getReturnType();
            if (!JsonResp.class.isAssignableFrom(clazz)) {
                return null;
            }

            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

            JsonResp result;

            if (e instanceof AppException) {
                AppException exception = (AppException) e;
                result = JsonResp.data().errorMsg(exception.getErrorCode(), ((AppException) e).getErrorMessage());
            } else if (e instanceof MethodArgumentNotValidException) {
                List<ObjectError> objectErrors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors();
                if (CollectionUtils.isNotEmpty(objectErrors)) {
                    result = JsonResp.data().errorMsg(ErrorCodeEnum.PARAM_VALIT_ERROR.getCode(), objectErrors.get(0).getDefaultMessage());
                } else {
                    result = JsonResp.data().errorMsg(ErrorCodeEnum.Default);
                }
            } else {
                if (PropertyConfigurer.getBoolean("env")) {
                    // 线上环境
                    result = JsonResp.data().errorMsg(ErrorCodeEnum.Default);
                } else {
                    // 开发/测试
                    SystemException systemException;
                    if (e instanceof DataIntegrityViolationException) {
                        systemException = new SystemException(e.getCause());
                    } else {
                        systemException = new SystemException(e);
                    }
                    if (systemException.getMessage() != null) {
                        result = JsonResp.data().errorMsg(ErrorCodeEnum.Default.getCode(), systemException.getMessage());
                    } else {
                        result = JsonResp.data().errorMsg(ErrorCodeEnum.Default);
                    }
                }
            }

            try (PrintWriter writer = response.getWriter()) {
                writer.write(result.toJson());
                writer.flush();
            } catch (IOException ex) {
                logger.error("response writer IOException:", ex);
            }
            return null;
        });
    }
}
