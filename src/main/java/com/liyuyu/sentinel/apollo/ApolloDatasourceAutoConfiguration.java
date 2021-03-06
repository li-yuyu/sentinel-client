package com.liyuyu.sentinel.apollo;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.util.AppNameUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ctrip.framework.apollo.ConfigService;

/**
 * @author Lyle
 * @since 2020年6月18日 下午7:48:23
 */
@Configuration
@ConditionalOnClass(ConfigService.class)
public class ApolloDatasourceAutoConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApolloDatasourceAutoConfiguration.class);

	public static final String NAMESPACE_NAME = "BASE.sentinel-rule";

	public static final String APP_NAME = AppNameUtil.getAppName();

	public static final String FLOW_RULE_KEY = ApolloConfigUtil.getFlowRuleKey(APP_NAME);
	public static final String DEFAULT_FLOW_RULES = "[]";

	public static final String DEGRADE_RULE_KEY = ApolloConfigUtil.getDegradeRuleKey(APP_NAME);
	public static final String DEFAULT_DEGRADE_RULES = "[]";

	public static final String SYSTEM_RULE_KEY = ApolloConfigUtil.getSystemRuleKey(APP_NAME);
	public static final String DEFAULT_SYSTEM_RULES = "[]";

	public static final String AUTHORITY_RULE_KEY = ApolloConfigUtil.getAuthorityRuleKey(APP_NAME);
	public static final String DEFAULT_AUTHORITY_RULES = "[]";

	public static final String PARAM_FLOW_RULE_KEY = ApolloConfigUtil.getParamFlowRuleKey(APP_NAME);
	public static final String DEFAULT_PARAM_FLOW_RULES = "[]";

	public ApolloDatasourceAutoConfiguration() {
		initialize();
	}

	private static void initialize() {
		ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ApolloDataSource<>(NAMESPACE_NAME,
				FLOW_RULE_KEY, DEFAULT_FLOW_RULES,
				source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
				}));
		FlowRuleManager.register2Property(flowRuleDataSource.getProperty());

		ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = new ApolloDataSource<>(NAMESPACE_NAME,
				DEGRADE_RULE_KEY, DEFAULT_DEGRADE_RULES,
				source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
				}));
		DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());

		ReadableDataSource<String, List<SystemRule>> systemRuleDataSource = new ApolloDataSource<>(NAMESPACE_NAME,
				SYSTEM_RULE_KEY, DEFAULT_SYSTEM_RULES,
				source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
				}));
		SystemRuleManager.register2Property(systemRuleDataSource.getProperty());

		ReadableDataSource<String, List<AuthorityRule>> authorityRuleDataSource = new ApolloDataSource<>(NAMESPACE_NAME,
				AUTHORITY_RULE_KEY, DEFAULT_AUTHORITY_RULES, source -> {
					List<JSONObject> rules = JSON.parseObject(source, new TypeReference<List<JSONObject>>() {
					});
					return rules.stream().map(n -> n.getObject("rule", AuthorityRule.class))
							.collect(Collectors.toList());
				});
		AuthorityRuleManager.register2Property(authorityRuleDataSource.getProperty());

		ReadableDataSource<String, List<ParamFlowRule>> paramFlowRuleDataSource = new ApolloDataSource<>(NAMESPACE_NAME,
				PARAM_FLOW_RULE_KEY, DEFAULT_PARAM_FLOW_RULES, source -> {
					List<JSONObject> rules = JSON.parseObject(source, new TypeReference<List<JSONObject>>() {
					});
					return rules.stream().map(n -> n.getObject("rule", ParamFlowRule.class))
							.collect(Collectors.toList());
				});
		ParamFlowRuleManager.register2Property(paramFlowRuleDataSource.getProperty());

		LOGGER.info("Loaded rules from Apollo");
	}
}
