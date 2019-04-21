package com.itmayiedu.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.itmayiedu.utils.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 主题模式
 * @author mayn
 * *代表一个字符，#代表多个
 */
public class TopicProducer {

	private static final String EXCHANGE_NAME = "topic_exchange";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		// 1.创建新的连接
		Connection connection = MQConnectionUtils.newConnection();
		
		// 2.创建通道
		Channel channel = connection.createChannel();
		
		// 3. 生产者绑定交换机，参数：交换机名称，交换机类型（如果没有绑定交换机和队列，消息就会丢失）
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		
		// 4.发送消息
		String msg = "msg-robbieliu";
		String routingKey = "log.info.info";
		channel.basicPublish(EXCHANGE_NAME, routingKey, false, null, msg.getBytes());
		System.out.println("生产者发送msg：" + msg);
		
		// 5.关闭通道、连接
		channel.close();
		connection.close();
	}
	
}
