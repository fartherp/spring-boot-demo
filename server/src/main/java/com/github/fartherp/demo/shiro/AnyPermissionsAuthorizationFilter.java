/*
 * Copyright (c) 2019. CK. All rights reserved.
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

public class AnyPermissionsAuthorizationFilter extends AuthorizationFilter {

    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = getSubject(request, response);
        return subject.getPrincipal() != null;
    }

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
