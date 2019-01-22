/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.demo.common.req;

import com.github.fartherp.demo.common.util.Patterns;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Author: CK
 * @Date: 2018/12/14 14:38
 */
public class RegisterReq {

    @NotBlank(message = "请输入用户名")
    private String userName;

    @NotBlank(message = "请输入密码")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$", message = "密码不少于6位数字、字母组成，区分大小写")
    private String password;

    @NotBlank(message = "请输入联系电话")
    @Patterns(regexps = {"^1([34578])\\d{9}$", "^((\\(\\d{2,3}\\))|(\\d{3}-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(-\\d{1,4})?$"}, message = "手机号/座机格式错误，请重新填写")
    private String phone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
