/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.demo.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Author: CK
 * @Date: 2018/12/21 10:02
 */
public class MD5Utils {
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
