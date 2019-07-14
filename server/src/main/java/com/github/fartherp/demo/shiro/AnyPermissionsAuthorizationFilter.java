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

import com.github.fartherp.demo.common.base.JsonResp;
import com.github.fartherp.demo.common.enums.ErrorCodeEnum;
import org.apache.http.HttpStatus;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/12/14
 */
public class AnyPermissionsAuthorizationFilter extends AuthorizationFilter {

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = getSubject(request, response);
        return subject.getPrincipal() != null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

        Subject subject = getSubject(request, response);
        if (subject.getPrincipal() == null) {
            writeJsonResp(JsonResp.data().errorMsg(ErrorCodeEnum.NOT_LOGIN), response, HttpStatus.SC_OK);
        }
        return false;
    }

    public static void writeJsonResp(JsonResp resp, ServletResponse response, int httpStatusCode){
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        httpResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpResponse.setStatus(httpStatusCode);

        try (PrintWriter writer = response.getWriter()) {
            writer.write(resp.toJson());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
