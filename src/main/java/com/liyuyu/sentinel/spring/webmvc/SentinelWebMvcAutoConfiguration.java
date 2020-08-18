package com.liyuyu.sentinel.spring.webmvc;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.DefaultBlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcConfig;

/**
 * @author Lyle
 * @since 2020年8月18日 下午2:56:02 
 */
@Configuration
@ConditionalOnProperty(name = "sentinel.webmvc.enabled")
public class SentinelWebMvcAutoConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		addSpringMvcInterceptor(registry);
	}

	private void addSpringMvcInterceptor(InterceptorRegistry registry) {
		SentinelWebMvcConfig config = new SentinelWebMvcConfig();
		config.setBlockExceptionHandler(new DefaultBlockExceptionHandler());
		config.setHttpMethodSpecify(true);
		config.setWebContextUnify(true);
		config.setOriginParser(request -> request.getHeader("X-Sentinel-Origin"));

		registry.addInterceptor(new SentinelWebInterceptor(config)).addPathPatterns("/**");
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
	}

	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
	}

	@Override
	public Validator getValidator() {
		return null;
	}

	@Override
	public MessageCodesResolver getMessageCodesResolver() {
		return null;
	}
}
