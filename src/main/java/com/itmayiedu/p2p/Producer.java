package com.itmayiedu.p2p;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.itmayiedu.utils.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Producer {

	private static final String QUEUE_NAME = "test_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		// 1.创建一个新的连接
		Connection connection = MQConnectionUtils.newConnection();
		
		// 2.创建通道
		Channel channel = connection.createChannel();
		
		// 3.创建一个队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		// 4.创建msg
		String msg = "msg-robbieliu";
		
		for (int i = 0; i < 50; i++) {
			// 5.生产者发送消息，集群环境默认轮训发送，均摊消费
			channel.basicPublish("", QUEUE_NAME, null, (msg + i).getBytes());
			System.out.println("生产者发送消息:" + msg + i);
		}
		
		channel.close();
		connection.close();

	}
}
