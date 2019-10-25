package cn.abc.def.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.abc.def.component.jms.QueueProducer;
import cn.abc.def.component.jms.TopicProducer;

@RestController
@RequestMapping("/jms")
public class JmsController {

	@Autowired
	private QueueProducer queueProducer;
	
	@Autowired
	private TopicProducer topicProducer;
	
	@RequestMapping("/sendToQueue")
	public String send(String content) {
		if (StringUtils.isEmpty(content)) content = "默认消息";
		queueProducer.push(content);
		return "sended.";
	}
	
	@RequestMapping("/sendToTopic")
	public String publish(String content) {
		if (StringUtils.isEmpty(content)) content = "默认消息";
		topicProducer.push(content);
		return "sended.";
	}
}
