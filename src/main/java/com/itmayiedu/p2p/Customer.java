package com.itmayiedu.p2p;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.itmayiedu.utils.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Customer {
	private static final String QUEUE_NAME = "test_queue";

	public static void main(String[] args) throws IOException, TimeoutException {
		System.out.println("消费者002启动...");
		// 1.获取连接
		Connection connection = MQConnectionUtils.newConnection();
		// 2.获取通道
		Channel channel = connection.createChannel();
		
		// 3.消费者绑定队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msgString = new String(body, "UTF-8");
				System.out.println("消费者获取消息:" + msgString);
				// autoAck为false，需要手动返回ack
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		
		// 4.监听队列，autoAck设置应答模式，true自动应答，消费失败了会自动补偿，false手动应答
		channel.basicConsume(QUEUE_NAME, false, defaultConsumer);

		// channel.close();
		// connection.close();
		
	}

}
