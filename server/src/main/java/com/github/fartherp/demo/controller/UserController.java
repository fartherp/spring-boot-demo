/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.demo.controller;

import com.github.fartherp.demo.common.base.JsonResp;
import com.github.fartherp.demo.common.enums.ErrorCodeEnum;
import com.github.fartherp.demo.common.exception.AppException;
import com.github.fartherp.demo.common.req.LoginReq;
import com.github.fartherp.demo.common.req.RegisterReq;
import com.github.fartherp.demo.manager.UserManager;
import com.github.fartherp.demo.shiro.MD5Utils;
import com.github.fartherp.demo.shiro.MySessionManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: CK
 * @Date: 2018/12/14 14:23
 */
@RestController
@RequestMapping("/demo/user")
public class UserController extends BasicsController {

    @Resource
    private UserManager userManager;

    @PostMapping(value = "/register", produces = "application/json;charset=UTF-8")
    public JsonResp register(@Valid @RequestBody RegisterReq req) {
        req.setPassword(MD5Utils.encrypt(req.getUserName(), req.getPassword()));
        userManager.register(req);
        return JsonResp.data();
    }

    @PostMapping(value = "/login", produces = "application/json;charset=UTF-8")
    public JsonResp login(@Valid @RequestBody LoginReq req) {
        JsonResp resp = null;
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(req.getUserName(), req.getPassword());
        try {
            subject.login(token);

            Map<String, String> mapData = new HashMap<>();
            mapData.put(MySessionManager.AUTHORIZATION, subject.getSession().getId().toString());
            resp = JsonResp.data(mapData);
        } catch (IncorrectCredentialsException e) {
            resp = JsonResp.data().errorMsg(ErrorCodeEnum.PASSWORD_ERROR);
        } catch (AuthenticationException e) {
            resp = JsonResp.data().errorMsg(ErrorCodeEnum.USER_NO_EXIST);
        } catch (Exception e) {
            throw new AppException(ErrorCodeEnum.Default);
        }
        return resp;
    }

    @PostMapping(value = "/logout", produces = "application/json;charset=UTF-8")
    public JsonResp logout() {
        SecurityUtils.getSubject().logout();
        return JsonResp.data().errorMsg(ErrorCodeEnum.NOT_LOGIN);
    }

    @PostMapping(value = "/getUserInfo", produces = "application/json;charset=UTF-8")
    public JsonResp getUserInfo() {
        return userManager.getUserInfo(getSecUser().getId());
    }
}
