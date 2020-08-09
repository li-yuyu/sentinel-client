package com.liyuyu.sentinel.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import com.google.common.base.Strings;

public class SentinelApplicationContextInitializer implements EnvironmentPostProcessor {
	private static final Logger logger = LoggerFactory.getLogger(SentinelApplicationContextInitializer.class);
	private static final String APOLLO_APP_ID = "app.id";
	private static final String SENTINEL_PROJECT_NAME = "project.name";

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment configurableEnvironment,
			SpringApplication springApplication) {
		if (System.getProperty(SENTINEL_PROJECT_NAME) != null) {
			return;
		}
		String propertyValue = configurableEnvironment.getProperty(APOLLO_APP_ID);
		if (Strings.isNullOrEmpty(propertyValue)) {
			return;
		}
		System.setProperty(SENTINEL_PROJECT_NAME, propertyValue);
		logger.info("project name is set to {} by project.name property from System Property", propertyValue);
	}

}
