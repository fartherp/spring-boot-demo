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
package com.github.fartherp.demo.dao;

import com.github.fartherp.dbtest.spring.UseDbUnit;
import com.github.fartherp.dbtest.spring.boot.test.autoconfigure.SpringBootBaseDbTestCase;
import com.github.fartherp.demo.pojo.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2019/4/8
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