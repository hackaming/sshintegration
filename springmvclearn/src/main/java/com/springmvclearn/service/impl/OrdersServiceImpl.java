package com.springmvclearn.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.springmvclearn.dao.impl.OrdersDaoImpl;
import com.springmvclearn.model.Orders;
import com.springmvclearn.service.OrdersManager;

@Service("OrderManager")
public class OrdersServiceImpl implements OrdersManager {
	
private OrdersDaoImpl OrdersDao; 

	@Override
	public void saveOrders(Orders order) {
		OrdersDao.saveOrders(order);		
	}

	@Override
	public void deleteOrders(Orders order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Orders> findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Orders> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Orders order) {
		// TODO Auto-generated method stub
		
	}

	public OrdersDaoImpl getOrdersDao() {
		return OrdersDao;
	}
	@Resource
	public void setOrdersDao(OrdersDaoImpl ordersDao) {
		OrdersDao = ordersDao;
	}

}
