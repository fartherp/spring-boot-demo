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
package com.github.fartherp.demo.common.util;

import com.github.fartherp.demo.common.exception.AppException;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/12/18
 */
public class FormatUtils {

    public final static DecimalFormat d = new DecimalFormat(",###");

    public final static List<String> htmls;

    public static final String PACKAGE_PATH = "classpath*:com/github/fartherp/demo/common";

    public static final String TEMPLATE_RESOURCE_PATH = PACKAGE_PATH + "/contractTemplate.html";

    static {
        String html = null;
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(TEMPLATE_RESOURCE_PATH);
            html = IOUtils.toString(resources[0].getInputStream(), Charset.forName("GBK"));
        } catch (IOException e) {
            throw new AppException("合同模板文件不存在");
        }
        String[] h = html.split("\\$\\$\\{}");
        htmls = Collections.unmodifiableList(Arrays.asList(h));
    }

    public static String format(BigDecimal b) {
        return d.format(b);
    }

    public static BigDecimal toBigDecimal(Integer s) {
        return s == null ? BigDecimal.ZERO : new BigDecimal(s);
    }
}
