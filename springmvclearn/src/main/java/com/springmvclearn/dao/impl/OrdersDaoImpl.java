package com.springmvclearn.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.springmvclearn.dao.OrdersDao;
import com.springmvclearn.model.Orders;

@Repository("OrderDao")
public class OrdersDaoImpl implements OrdersDao {
	private HibernateTemplate hibernatetemplate;

	public HibernateTemplate getHibernatetemplate() {
		return hibernatetemplate;
	}

	@Resource
	public void setHibernatetemplate(HibernateTemplate hibernatetemplate) {
		this.hibernatetemplate = hibernatetemplate;
	}

	@Override
	public void saveOrders(Orders order) {
		hibernatetemplate.save(order);

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

}
