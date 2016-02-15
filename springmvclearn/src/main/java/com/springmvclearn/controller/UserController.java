package com.springmvclearn.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.springmvclearn.dao.impl.UserDaoImpl;
import com.springmvclearn.model.Orders;
import com.springmvclearn.model.Project;
import com.springmvclearn.model.User;
import com.springmvclearn.order.OrderProcessingGetFromMQAndSaveToDB;
import com.springmvclearn.order.OrderProduceAndSendToRabbitMQ;
import com.springmvclearn.order.OrderSerialize;
import com.springmvclearn.service.OrdersManager;
import com.springmvclearn.service.ProjectManager;
import com.springmvclearn.service.UserManager;
import com.springmvclearn.service.impl.UserServiceImpl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

@Controller
public class UserController  {
	private static Logger logger = Logger.getLogger(UserController.class);
	private final static String QUEUE_NAME = "hello";
	private UserManager um;
	private ProjectManager pm;
	private OrdersManager om;
	
	@Resource
	public void setOm(OrdersManager om) {
		this.om = om;
	}
	public OrdersManager getOm() {
		return om;
	}

	public UserManager getUm() {
		return um;
	}
	@Resource
	public void setUm(UserManager um) {
		this.um = um;
	}
	public ProjectManager getPm() {
		return pm;
	}
	@Resource
	public void setPm(ProjectManager pm) {
		this.pm = pm;
	}
	@RequestMapping("/register.do")
	public String userRegister(String userName, String password, String password2) {
		System.out.println("User controller was called");
		if (null == userName || null == password){
			return "/views/register/register";
		}
		User user = new User();
		if (!password.equals(password2)) {
			System.out.println("The password are not idential!");
			return "/views/register/register";
			
		} else {
			user.setPassword(password);
			user.setUserName(userName);
			um.saveUser(user);
			return "redirect:/login.do";
		}
	}

	@RequestMapping("/login.do")
	public String userLogin(String userName, String password,HttpServletRequest request) {
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		if (um.loginUser(user)) {
			System.out.println("Login success!");
			logger.debug("Login success!");
			request.getSession().setAttribute("User", user);
			System.out.println("The session in userlogin is:" + request.getSession());
			System.out.println("Now the value of user in session in userlogin is:" + request.getSession().getAttribute("User"));
			return "redirect:/crowdhome.do";
		} else {
			System.out.println("Login Error!");
			logger.debug("Login error");
			return "/views/login/login";
		}

	}
	@RequestMapping("/crowdhome.do")
	public String crowdhome(ModelMap m){
		List<Project> ls = new ArrayList<Project> ();
		logger.debug("Now begin to call the findall, the pm value is:" + pm);
		ls = pm.findAll();
/*		request.getSession().setAttribute("Projects", ls);
		System.out.println("from crowdhome in user controller, Now set the found projects into session" + request.getSession().getAttribute("Projects") );*/
		logger.debug("how show the projects home page");
		//debug
/*		Iterator iter = ls.iterator();
		while (iter.hasNext()){
			Project p = (Project) iter.next();
			System.out.println(p.getProjectName());
		}*/
		m.put("Projects", ls);
		return "/views/crowdhome/crowdhome";
	}
	
	@RequestMapping("/projectdetail.do")
	public String projectdetail(@RequestParam("id") String id,ModelMap m){
		System.out.println("get ID is :" + id);
		//now get the user from session, generator an order, put it in request,send to mq, then inform to the user 
		//that the order is being proceed, please wait.
		//another thread get order from mq and deal with
		Project project =(Project) pm.findById(Integer.parseInt(id));
		m.put("Project", project);
		System.out.println("Now put the project object into model map in projectdetail user controller:" + project);
		return "/views/order/projectdetail";
	}
	
	@RequestMapping("/buy.do")
	public String order(@RequestParam("amount") int amount,@RequestParam("projectid") int projectid,HttpServletRequest request) throws Exception {
		System.out.println("now get amount and id from projectdetail.jsp the value is:"+amount+projectid);
		Orders order = new Orders();
		order.setProjectid(projectid);
		order.setPurchaseamount(amount);
		User u = (User)request.getSession().getAttribute("User");
		order.setUserid(u.getId());
		// om.saveOrders(order); do not save it in db first, just send to the server.
		System.out.println("Now begin to new a OrderProduceAndSendToRabbitMQ, and call the method to send data to the server");
		OrderProduceAndSendToRabbitMQ ortrm = new OrderProduceAndSendToRabbitMQ();
		System.out.println("now call the sendtoserver to send to the server");
		ortrm.sendToServer(order); //send to the server after sent, forward to pay...
		//return "redirect:/ordercenter.do";
		System.out.println("Now return, redirect to pay.do");
		return "redirect:/pay.do";
	}
	@RequestMapping("ordercenter.do")
	public String getorder(HttpServletRequest request,ModelMap m){
		User u = (User) request.getSession().getAttribute("User");
		ArrayList<Orders> o = (ArrayList<Orders>)om.findById(u.getId());
		m.put("Orders", o);
		
		return "/views/order/ordercenter";
	}
	@RequestMapping("pay.do")
	public String pay() throws Exception{
/*		System.out.println("Now new a OrderProcessingGetFromMQAndSaveToDB in pay.do");
		OrderProcessingGetFromMQAndSaveToDB opbrm = new OrderProcessingGetFromMQAndSaveToDB();
		System.out.println("Now call the getFromServer which will get from mq and save it into DB");
		opbrm.getFromServer();*/
		//opbrm.getFromServer();
		logger.debug("Now commented the old code which will new a new class. Now we put the get order from mq and save it into DB code in usercontroller because the injection has always been failured.");
		return "/views/order/pay";	
	}
	public UserController() throws Exception{
/*		logger.debug("Now the Controller's initialized, begin to call the getFromServer, it will wait for any request and save it into db.");
		getFromServer();
		*/
		//put these cldes in another seperated project named processorder
	}
}
