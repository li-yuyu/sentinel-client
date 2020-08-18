package com.liyuyu.sentinel.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import com.google.common.base.Strings;

/**
 * @author Lyle
 * @since 2020年8月12日 下午3:09:14 
 */
public class SentinelApplicationContextInitializer implements EnvironmentPostProcessor {
	private static final Logger logger = LoggerFactory.getLogger(SentinelApplicationContextInitializer.class);
	private static final String APOLLO_APP_ID = "app.id";
	private static final String SENTINEL_PROJECT_NAME = "project.name";
	private static final String SENTINEL_DASHBOARD_SERVER = "csp.sentinel.dashboard.server";

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment configurableEnvironment,
			SpringApplication springApplication) {
		initializeProjectName(configurableEnvironment);
		initializeDashbordServer(configurableEnvironment);
	}

	private void initializeProjectName(ConfigurableEnvironment configurableEnvironment) {
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

	private void initializeDashbordServer(ConfigurableEnvironment configurableEnvironment) {
		if (System.getProperty(SENTINEL_DASHBOARD_SERVER) != null) {
			return;
		}
		String dashboardServer = configurableEnvironment.getProperty(SENTINEL_DASHBOARD_SERVER);
		if (Strings.isNullOrEmpty(dashboardServer)) {
			return;
		}
		System.setProperty(SENTINEL_DASHBOARD_SERVER, dashboardServer);
		logger.info("dashboard server is set to {} by csp.sentinel.dashboard.server property from System Property",
				dashboardServer);
	}

}
