/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.demo.common.exception;

import com.github.fartherp.demo.common.enums.ErrorCodeEnum;

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
