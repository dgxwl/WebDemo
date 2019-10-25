package cn.abc.def.component.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;

/**
 * spring-jms示例
 * 点对点模式和发布订阅模式共用的监听类
 * 需要继承javax.jms.MessageListener
 */
@Component
public class MyMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;
			System.out.println("接收到的消息: " + textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
