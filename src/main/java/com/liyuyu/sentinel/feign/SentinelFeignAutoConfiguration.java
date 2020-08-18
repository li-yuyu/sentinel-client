package com.liyuyu.sentinel.feign;

import com.alibaba.csp.sentinel.SphU;
import feign.Feign;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ConditionalOnClass({ SphU.class, Feign.class })
public class SentinelFeignAutoConfiguration {

	@Bean
	@Scope("prototype")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(name = "sentinel.feign.enabled")
	public Feign.Builder feignSentinelBuilder() {
		return SentinelFeign.builder();
	}

}
