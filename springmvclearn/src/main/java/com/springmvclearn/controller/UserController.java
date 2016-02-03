package com.springmvclearn.controller;

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

import com.springmvclearn.dao.impl.UserDaoImpl;
import com.springmvclearn.model.Project;
import com.springmvclearn.model.User;
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
public class UserController {
	private static Logger logger = Logger.getLogger(UserController.class);
	private UserManager um;
	private ProjectManager pm;

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
	@RequestMapping("/buy.do")
	public String buy(@RequestParam("id") String id){
		System.out.println("get ID is :" + id);
		//now get the user from session, generator an order, put it in request,send to mq, then inform to the user 
		//that the order is being proceed, please wait.
		//another thread get order from mq and deal with
		return "/views/order/order";
	}


}
