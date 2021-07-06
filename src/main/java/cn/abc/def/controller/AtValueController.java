package cn.abc.def.controller;

import java.util.Arrays;

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
	
	@Value("${test.acquireNotExist:qweqwe}")  //获取一个不存在的参数, 得到冒号右边的默认值
	private String acquireNotExist;
	
	@Value("${test.acquireEmpty:ewqewq}")  //获取一个存在的参数, 但值是空串, 获取到的是空串而不是冒号右边的默认值
	private String acquireEmpty;
	
//	@Value("${test.numArr}")  //获取一个数字类型数组, 实测不支持, 启动项目报错
//	private Integer[] numArr;
	
	@Value("${test.strArr}")  //获取一个字符串类型数组
	private String[] strArr;
	
	@Value("${test.defaultStrArr:def,aul,t!!}")  //获取一个不存在的数组参数, 获取到默认值["def", "aul", "t!!"]
	private String[] defaultStrArr;

	@RequestMapping("/at_value")
	public String atValue() {
		System.out.println(testVal);
		System.out.println(acquireEmpty);
		System.out.println(acquireNotExist);
//		System.out.println(Arrays.toString(numArr));
		System.out.println(Arrays.toString(strArr));
		System.out.println(Arrays.toString(defaultStrArr));
		return testVal;
	}
}
