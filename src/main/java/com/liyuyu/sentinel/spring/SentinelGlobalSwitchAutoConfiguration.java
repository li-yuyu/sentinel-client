package com.liyuyu.sentinel.spring;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.alibaba.csp.sentinel.Constants;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;

/**
 * @author Lyle
 * @since 2020年9月8日 下午3:37:03 
 */
@Configuration
public class SentinelGlobalSwitchAutoConfiguration {

	@Value("${sentinel.global.switch:true}")
	public boolean on;

	@PostConstruct
	private void initialize() {
		Constants.ON = on;
	}

	@ApolloConfigChangeListener
	private void someOnChange(ConfigChangeEvent changeEvent) {
		if (changeEvent.isChanged("sentinel.global.switch")) {
			ConfigChange change = changeEvent.getChange("sentinel.global.switch");
			Constants.ON = Boolean.valueOf(change.getNewValue());
		}
	}

}
