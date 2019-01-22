/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.demo.common.enums;

import com.alibaba.fastjson.JSON;
import com.github.fartherp.demo.common.exception.AppException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.fartherp.demo.common.util.FormatUtils.PACKAGE_PATH;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/12/19
 */
public enum PlaceCode {
    CODE
    ;
    public final Map<String, List<String>> unmodifiableMap;

    public static final String FQ_RESOURCE_PATH = PACKAGE_PATH + "/newcitys.json";

    public final List<List<String>> airports;

    PlaceCode() {
        String html = null;
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(FQ_RESOURCE_PATH);
            html = IOUtils.toString(resources[0].getInputStream());
        } catch (IOException e) {
            throw new AppException("城市配置文件不存在");
        }
        List<City> cityList = JSON.parseArray(html, City.class);
        Map<String, List<String>> map = new HashMap<>();
        List<List<String>> listList = new ArrayList<>();
        listList.add(new ArrayList<>());
        cityList.forEach(c -> {
            List<String> list = new ArrayList<>(2);
            list.add(StringUtils.trim(c.getName()));
            list.add(StringUtils.trim(c.getParent()));
            map.put(StringUtils.trim(c.getValue()), list);

            List<String> list1 = new ArrayList<>(2);
            list1.add(c.getLongitude() + "");
            list1.add(c.getLatitude() + "");
            listList.add(list1);
        });
        unmodifiableMap = Collections.unmodifiableMap(map);
        airports = Collections.unmodifiableList(listList);
    }

    public Map<String, List<String>> getMap() {
        return unmodifiableMap;
    }

    public static String getProvince(String code) {
        return CODE.getMap().get(code).get(0);
    }

    public static String getCity(String code) {
        return CODE.getMap().get(code).get(1);
    }

    public static String getAll(String code, String separate) {
        List<String> list = CODE.getMap().get(code);
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }
        String parent = list.get(1);
        if (parent == null) {
            return list.get(0);
        }
        return list.get(0) + separate + CODE.unmodifiableMap.get(parent).get(0);
    }
}
