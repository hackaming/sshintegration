package com.springmvclearn.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.springmvclearn.model.Orders;

public class OrderSerialize {
	public String OrderToStringConvert(Orders order){
	    SerializerFeature feature = SerializerFeature.DisableCircularReferenceDetect;  
	    String jsonStrng  = JSON.toJSONString(order);
	    System.out.println("The order has been convert to string:" + jsonStrng);
		return jsonStrng;
	}
	public Orders StringToOrder(String orderString){
		Orders order = JSON.parseObject(orderString ,Orders.class);
		System.out.println("The string has been converted to object:" + order);
		return order;
	}
}
