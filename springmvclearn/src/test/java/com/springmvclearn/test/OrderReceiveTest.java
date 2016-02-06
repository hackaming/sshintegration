package com.springmvclearn.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.springmvclearn.order.OrderProcessingByRabbitMQ;

public class OrderReceiveTest {
	public static void main(String argv[]) throws Exception{
		ApplicationContext ctx = new FileSystemXmlApplicationContext("beans.xml");
		OrderProcessingByRabbitMQ opbrm = new OrderProcessingByRabbitMQ();
		opbrm.getFromServer();
	}

}
