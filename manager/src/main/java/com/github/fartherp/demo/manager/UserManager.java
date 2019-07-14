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