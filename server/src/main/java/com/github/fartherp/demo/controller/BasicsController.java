/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.demo.controller;

import com.github.fartherp.demo.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/12/13
 */
public class BasicsController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected User getSecUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }
}
