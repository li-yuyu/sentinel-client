package com.liyuyu.sentinel.spring;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Eric Zhao
 */
@Configuration
public class SentinelAopAutoConfiguration {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
