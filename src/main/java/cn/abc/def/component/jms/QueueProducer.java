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
 * 点对点消息队列生产者
 */
@Component
public class QueueProducer {

	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination queueDestination;
	
	public void push(String text) {
		jmsTemplate.send(queueDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(text);
			}
		});
	}
}
