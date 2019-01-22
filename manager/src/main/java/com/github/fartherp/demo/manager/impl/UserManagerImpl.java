/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.demo.manager.impl;

import com.github.fartherp.demo.common.base.JsonResp;
import com.github.fartherp.demo.common.req.RegisterReq;
import com.github.fartherp.demo.manager.UserManager;
import com.github.fartherp.framework.database.dao.DaoMapper;
import com.github.fartherp.framework.database.service.impl.GenericSqlMapServiceImpl;
import com.github.fartherp.demo.dao.UserMapper;
import com.github.fartherp.demo.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * `user` 
 */
@Service("userManager")
public class UserManagerImpl extends GenericSqlMapServiceImpl<User, Integer> implements UserManager {

    @Resource
    private UserMapper userMapper;

    public DaoMapper<User, Integer> getDao() {
        return userMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterReq req) {
        User user = new User();
        BeanUtils.copyProperties(req, user);
        userMapper.insertSelective(user);
    }

    public JsonResp getUserInfo(Integer userId) {
        User userInfoVo = userMapper.selectByPrimaryKey(userId);
        return JsonResp.data(userInfoVo);
    }

    public User findByUsername(String userName) {
        return userMapper.findByUsername(userName);
    }
}