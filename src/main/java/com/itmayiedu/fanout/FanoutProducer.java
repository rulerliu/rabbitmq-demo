package com.itmayiedu.fanout;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.itmayiedu.utils.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 广播模式
 * @author mayn
 *
 */
public class FanoutProducer {

	private static final String EXCHANGE_NAME = "fanout_exchange";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		System.out.println("生产者启动...");
		// 1.建立MQ连接
		Connection connection = MQConnectionUtils.newConnection();
		
		// 2.建立通道
		Channel channel = connection.createChannel();
		
		// 3. 生产者绑定交换机，参数：交换机名称，交换机类型（如果没有绑定交换机和队列，消息就会丢失）
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		
		// 4.发送消息，没有routingKey
		String msg = "robbieliu";
		channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
		System.out.println("生产者发送消息：" + msg);
		
		// 5.关闭通道和连接
		channel.close();
		connection.close();
		
	}
	
}
