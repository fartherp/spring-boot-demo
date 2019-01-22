/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.demo;

import com.github.fartherp.demo.base.WebMvcConfig;
import com.github.fartherp.demo.shiro.ShiroConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class, scanBasePackages={"com.github.fartherp.demo"})
@Import({WebMvcConfig.class, ShiroConfig.class})
public class ServiceApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ServiceApplication.class);
    }
}