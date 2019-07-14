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

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/11/27
 */
public class AppException extends RuntimeException {
    private int errorCode = -1;
    private String errorMessage;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public AppException(String msg) {
        super(msg);
        this.errorMessage = msg;
    }

    public AppException(Integer errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
        this.errorMessage = msg;
    }

    public AppException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getDesc());
        this.errorCode = errorCodeEnum.getCode();
        this.errorMessage = errorCodeEnum.getDesc();
    }

    public AppException(Integer errorCode, String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());

        this.errorCode = errorCode;
        this.errorMessage = msg;
    }
}
