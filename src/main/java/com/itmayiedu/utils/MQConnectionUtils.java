package com.itmayiedu.utils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MQConnectionUtils {
	
	public static Connection newConnection() throws IOException, TimeoutException {
		// 1.创建连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		
		// 2.设置连接地址，用户名，密码，amqp端口号，virtual host
		factory.setHost("127.0.0.1");
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setPort(5672);
		factory.setVirtualHost("/guest-host");
		
		Connection connection = factory.newConnection();
		return connection;
	}

}
