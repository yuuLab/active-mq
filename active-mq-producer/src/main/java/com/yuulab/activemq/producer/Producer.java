package com.yuulab.activemq.producer;

import java.io.Serializable;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.jms.JMSException;
import jakarta.jms.ObjectMessage;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnection;
import jakarta.jms.QueueConnectionFactory;
import jakarta.jms.QueueSession;
import jakarta.jms.Session;

public class Producer {

	/**
	 *  キューにオブジェクトデータを送信する。
	 *  
	 * @param data  送信データ
	 * @return JMS Message ID
	 */
	public String send(Serializable data) {
		InitialContext initialContext;
		QueueConnectionFactory factory;
		try {
			initialContext = new InitialContext();
			factory = (QueueConnectionFactory) initialContext.lookup("ConnectionFactory");
		} catch (NamingException e) {
			throw new RuntimeException("名前解決に失敗しました。", e);
		}

		try (QueueConnection connection = factory.createQueueConnection();
				QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);) {
			connection.start();
			// create message
			ObjectMessage message = session.createObjectMessage(data);
			// send
			Queue originalQueue = (Queue) initialContext.lookup("Queue");
			session.createSender(originalQueue).send(message);
			System.out.println("ActiveMQにメッセージを送信しました。" + message.getJMSMessageID());
			connection.close();
			return message.getJMSMessageID();
		} catch (JMSException | NamingException e) {
			throw new RuntimeException("送信に失敗しました。", e);
		}
	}
}
