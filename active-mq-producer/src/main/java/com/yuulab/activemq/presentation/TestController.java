package com.yuulab.activemq.presentation;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuulab.activemq.data.TestQueueData;
import com.yuulab.activemq.producer.Producer;

import lombok.Getter;

@RestController
public class TestController {

	@GetMapping(value = "/test")
	public Status get(@RequestParam(name = "fistName", defaultValue = "taro") String firstName,
			@RequestParam(name = "lastName", defaultValue = "tanaka") String lastName) {
		Producer pub = new Producer();
		TestQueueData data = new TestQueueData(this.generateId(), firstName, lastName);
		pub.send(data);

		return new Status("OK");
	}

	@Getter
	class Status {
		private final String status;

		public Status(String status) {
			this.status = status;
		}
	}

	private String generateId() {
		SecureRandom sr;
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
			return String.valueOf(sr.nextInt());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("IDの生成に失敗しました。", e);
		}
	}
}
