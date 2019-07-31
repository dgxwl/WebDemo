package cn.abc.def.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用@Value注入配置文件中的配置项值
 * 当前类必须为spring管理的bean
 * 需要在spring配置文件中加上<context:property-placeholder location="classpath:application.properties" />
 */
@RestController
public class AtValueController {
	
	@Value("${test.val}")
	private String testVal;  //必须是实例成员

	@RequestMapping("/at_value")
	public String atValue() {
		return testVal;
	}
}
