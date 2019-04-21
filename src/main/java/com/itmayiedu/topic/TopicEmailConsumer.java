package com.itmayiedu.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.itmayiedu.utils.MQConnectionUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class TopicEmailConsumer {

	private static final String QUEUE_NAME = "topic_email_quque";
	private static final String EXCHANGE_NAME = "topic_exchange";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		System.out.println("邮件消费者启动");
		// 1.创建新的连接
		Connection connection = MQConnectionUtils.newConnection();
		
		// 2.创建通道
		Channel channel = connection.createChannel();
		
		// 3.消费者申明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		// 4.消费者队列绑定交换机，*代表一个字符 （参数1 队列 参数2交换机 参数3 routingKey）
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "log.*");
		
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body, "UTF-8");
				System.out.println("邮件消费者获取消息:" + msg);
			}
		};
		
		// 5.消费者监听队列消息
		channel.basicConsume(QUEUE_NAME, true, consumer);

		
	}
	
}
