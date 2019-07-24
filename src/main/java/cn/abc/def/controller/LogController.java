package cn.abc.def.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

	final static Logger logger = LoggerFactory.getLogger(LogController.class);
	
	@RequestMapping("/log")
	public String logback() {
		//级别由低到高, 但配置文件中有声明的最低级为info, 因此trace和debug不会输出
		logger.trace("I'm trace");
		logger.debug("I'm debug");
		logger.info("I'm info");
		logger.warn("I'm warn");
		logger.error("I'm error");
		
		//格式化输出
		logger.error("第{}个坑, 第{}个坑, 第{}个坑", 1, "贰", "III");
		
		return "ok";
	}
}
