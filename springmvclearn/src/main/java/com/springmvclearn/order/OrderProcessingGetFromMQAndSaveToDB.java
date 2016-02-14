package com.springmvclearn.order;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.springmvclearn.dao.OrdersDao;
import com.springmvclearn.model.Orders;
import com.springmvclearn.model.Project;
import com.springmvclearn.service.OrdersManager;

public class OrderProcessingGetFromMQAndSaveToDB {
	private final static String QUEUE_NAME = "hello";
	@Resource
	private OrdersManager om;
	@Resource
	private static OrdersDao od;

	public OrdersDao getOd() {
		return od;
	}

	public void setOd(OrdersDao od) {
		this.od = od;
	}

	public OrdersManager getOm() {
		return om;
	}

	public void setOm(OrdersManager om) {
		this.om = om;
	}
public void test(){
	ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
	System.out.println("We have these beans:"+ctx.getBeanDefinitionNames());
}
	public void getFromServer() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri("amqp://test:password@localhost:5672/%2F");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String orderString = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + orderString + "'");
				OrderSerialize os = new OrderSerialize();
				Orders order = os.StringToOrder(orderString);
				System.out.println("Now we get the order:" + order + "now save it into db");
				//test();
				if (null == od) {
					System.out.println("The injected orderDAO is null, exception shows, the order has not been saved into DB ");
					return;
				} else {
					od.saveOrders(order);
				}
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}
