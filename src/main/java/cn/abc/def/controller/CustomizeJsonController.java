package cn.abc.def.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.abc.def.entity.JsonCusUser;

/**
 * 演示定制json字段返回的格式, 以date类型返回时间戳为例
 * @author Administrator
 *
 */
@RestController
public class CustomizeJsonController {

	@RequestMapping("/json_cus")
	public Object jsonCustomize(String name) {
		JsonCusUser user = new JsonCusUser();
		user.setName(name);
		user.setSignUpDate(new Date());
		return user;
	}
}
