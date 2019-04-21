package com.itmayiedu.fanout;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.itmayiedu.utils.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class FanoutEmailConsumer {

	private static final String QUEUE_NAME = "fanout_email_queue";
	
	private static final String EXCHANGE_NAME = "fanout_exchange";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		System.out.println("邮件消费者启动...");
		// 1.建立MQ连接
		Connection connection = MQConnectionUtils.newConnection();

		// 2.建立通道
		Channel channel = connection.createChannel();
		
		// 3.消费者申明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		// 4.消费者队列绑定交换机 （参数1 队列 参数2交换机 参数3 routingKey）
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
		
		DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msgString = new String(body, "UTF-8");
				System.out.println("邮件消费者获取消息:" + msgString);
			}
		};
		channel.basicConsume(QUEUE_NAME, true, defaultConsumer);
		
	}

}
