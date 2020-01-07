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
package com.github.fartherp.demo.controller;

import com.github.fartherp.demo.common.base.JsonResp;
import com.github.fartherp.demo.common.enums.ErrorCodeEnum;
import com.github.fartherp.demo.common.exception.AppException;
import com.github.fartherp.demo.common.req.LoginReq;
import com.github.fartherp.demo.common.req.RegisterReq;
import com.github.fartherp.demo.manager.UserManager;
import com.github.fartherp.demo.shiro.Md5Utils;
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
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/12/14
 */
@RestController
@RequestMapping("/demo/user")
public class UserController extends BasicsController {

    @Resource
    private UserManager userManager;

    @PostMapping(value = "/register", produces = "application/json;charset=UTF-8")
    public JsonResp register(@Valid @RequestBody RegisterReq req) {
        req.setPassword(Md5Utils.encrypt(req.getUserName(), req.getPassword()));
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

            Map<String, String> mapData = new HashMap<>(1);
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
