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

    @Override
    public DaoMapper<User, Integer> getDao() {
        return userMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterReq req) {
        User user = new User();
        BeanUtils.copyProperties(req, user);
        userMapper.insertSelective(user);
    }

    @Override
    public JsonResp getUserInfo(Integer userId) {
        User userInfoVo = userMapper.selectByPrimaryKey(userId);
        return JsonResp.data(userInfoVo);
    }

    @Override
    public User findByUsername(String userName) {
        return userMapper.findByUsername(userName);
    }
}