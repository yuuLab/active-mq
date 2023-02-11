package com.yuulab.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.yuulab.activemq.data.TestQueueData;

@Component
public class TestQueueListener {

	/**
	 * TestQueueからメッセージを受け取る。
	 * @param data メッセージ
	 * @throws InterruptedException
	 */
	@JmsListener(destination = "TestQueue")
	public void receive(TestQueueData data) throws InterruptedException {
		System.out.println("メッセージを受信しました。" + data);
		// dummy sleep
		Thread.sleep(10000L);
	}
}
