package cn.abc.def.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.abc.def.util.RedisUtil;

@RestController
public class RedisController {

	@RequestMapping("/test_redis/{value}")
	public String testRedis(@PathVariable String value) {
		RedisUtil.set("test", value);
		String test = RedisUtil.get("test");
		RedisUtil.delete("test");
		
		return test;
	}
}
