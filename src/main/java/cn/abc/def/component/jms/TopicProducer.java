package cn.abc.def.component.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 * spring-jms示例
 * 发布订阅模式生产者
 */
@Component
public class TopicProducer {

	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination topicDestination;
	
	public void push(String text) {
		jmsTemplate.send(topicDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(text);
			}
		});
	}
}
