package com.yuulab.activemq.data;

import java.io.Serializable;

public record TestQueueData(
		String id,
		String firstName,
		String lastName) implements Serializable {
}