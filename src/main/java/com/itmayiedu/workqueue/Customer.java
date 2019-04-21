package com.itmayiedu.workqueue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.itmayiedu.utils.MQConnectionUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
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
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		channel.basicQos(1);// 保证一次只分发一次 限制发送给同一个消费者 不得超过一条消息
		
		DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				System.out.println("消费者获取消息:" + new String(body, "UTF-8"));
				
				try {
					// 模拟处理业务耗时场景
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// autoAck为false，需要手动返回ack
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			} 
		};
		
		// 3.监听队列，autoAck设置应答模式，true自动应答，消费失败了会自动补偿，false手动应答
		// 工作队列，autoAck必须费false
		channel.basicConsume(QUEUE_NAME, false, defaultConsumer);

		// channel.close();
		// connection.close();
		
	}

}
