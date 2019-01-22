/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.demo.common.enums;

import org.junit.jupiter.api.Test;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/12/19
 */
public class PlaceCodeTest {
    @Test
    public void getAll() throws Exception {
        System.out.println(PlaceCode.getAll("110000", ","));
        System.out.println(PlaceCode.getAll("140322", ","));
    }

}