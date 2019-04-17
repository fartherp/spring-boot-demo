package com.github.fartherp.demo.dao;

import com.github.fartherp.dbtest.dbunit.UseDbUnit;
import com.github.fartherp.dbtest.spring.boot.test.autoconfigure.SpringBootBaseDbTestCase;
import com.github.fartherp.demo.pojo.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2019/4/8
 */
@SpringBootApplication
public class UserMapperTest extends SpringBootBaseDbTestCase {

    @Resource
    private UserMapper userMapper;

    @Test
    @UseDbUnit(tables = "tb_user" )
    public void testSelectByPrimaryKey() throws Exception {
        User user = userMapper.selectByPrimaryKey(1);
        Assert.assertEquals(user.getUserName(), "name1");
        Assert.assertEquals(user.getPassword(), "password1");
        Assert.assertEquals(user.getPhone(), "18611111111");
    }
}