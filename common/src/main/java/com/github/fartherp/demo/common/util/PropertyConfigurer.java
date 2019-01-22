/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.demo.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("demoPropertyConfigurer")
public class PropertyConfigurer extends PropertySourcesPlaceholderConfigurer {

	private static ConfigurablePropertyResolver configurablePropertyResolver;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
									 ConfigurablePropertyResolver propertyResolver) throws BeansException {
		super.processProperties(beanFactoryToProcess, propertyResolver);
		configurablePropertyResolver = propertyResolver;
	}

	public static String getStringValue(String key) {
		return configurablePropertyResolver.getProperty(key);
	}

	public static boolean getBoolean(String key) {
		return Optional.ofNullable(key).map((o) -> {
			String flag = configurablePropertyResolver.getProperty(key);
			return Boolean.valueOf(flag);
		}).orElse(false);
	}
}
