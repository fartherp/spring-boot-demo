/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.demo.manager;

import com.github.fartherp.demo.common.base.JsonResp;
import com.github.fartherp.demo.common.req.RegisterReq;
import com.github.fartherp.framework.database.service.GenericService;
import com.github.fartherp.demo.pojo.User;

/**
 * `user` 
 */
public interface UserManager extends GenericService<User, Integer> {

    void register(RegisterReq req);

    JsonResp getUserInfo(Integer userId);

    User findByUsername(String userName);
}