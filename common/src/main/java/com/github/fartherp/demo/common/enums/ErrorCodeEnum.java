/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.demo.common.enums;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/4/13
 */
public enum ErrorCodeEnum {

    Default(-1, "系统错误"),
    PARAM_VALIT_ERROR(1, "请求参数错误"),
    SYSTEM_CONFIG_ERROR(2, "系统配置错误"),
    REPEAT_SUBMIT (3,"重复提交"),
    RECORD_NOT_EXIST(4,"记录不存在"),
    USER_NO_EXIST(5,"用户不存在"),
    NOT_LOGIN(6,"未登录"),
    PASSWORD_ERROR(7,"密码错误"),
    ;

    public int code;
    public String desc;

    ErrorCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
