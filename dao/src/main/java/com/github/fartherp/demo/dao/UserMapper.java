/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.demo.dao;

import com.github.fartherp.framework.database.dao.DaoMapper;
import com.github.fartherp.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * `user` 
 */
@Mapper
public interface UserMapper extends DaoMapper<User, Integer> {

    User findByUsername(@Param("userName") String userName);
}