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
package com.github.fartherp.demo.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/12/21
 */
public class Md5Utils {
    /**
     * md5加密工具
     */
    public static String encrypt(String userName, String password) {
        String hashAlgorithmName = "MD5";
        int hashIterations = 2;
        ByteSource credentialsSalt = ByteSource.Util.bytes(userName);
        Object obj = new SimpleHash(hashAlgorithmName, password, credentialsSalt, hashIterations);
        return obj.toString();
    }

}
