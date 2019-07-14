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
package com.github.fartherp.demo.common.enums;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/4/13
 */
public enum ErrorCodeEnum {

    Default(-1, "系统错误"),
    PARAM_VALIT_ERROR(1, "请求参数错误"),
    SYSTEM_CONFIG_ERROR(2, "系统配置错误"),
    REPEAT_SUBMIT (3,"重复提交"),
    RECORD_NOT_EXIST(4,"记录不存在"),
    USER_NO_EXIST(5,"用户不存在"),
    NOT_LOGIN(6,"未登录"),
    PASSWORD_ERROR(7,"密码错误");

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
