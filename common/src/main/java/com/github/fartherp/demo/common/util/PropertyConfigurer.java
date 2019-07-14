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
