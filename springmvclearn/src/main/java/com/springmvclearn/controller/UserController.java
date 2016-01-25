package com.springmvclearn.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springmvclearn.dao.impl.UserDaoImpl;
import com.springmvclearn.model.User;
import com.springmvclearn.service.UserManager;
import com.springmvclearn.service.impl.UserServiceImpl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
	private static Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private UserManager um;

	public UserManager getUm() {
		return um;
	}

	public void setUm(UserManager um) {
		this.um = um;
	}

	@RequestMapping("/views/register/register.do")
	public String userRegister(String userName, String password, String password2) {
		System.out.println("User controller was called");
		User user = new User();
		if (!password.equals(password2)) {
			System.out.println("The password are not idential!");
			return "/views/register/register";
			
		} else {
			user.setPassword(password);
			user.setUserName(userName);
			um.saveUser(user);
			return "redirect:/views/login/login.jsp";
		}
	}

	@RequestMapping("/views/login/login.do")
	public String userLogin(String userName, String password) {
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		if (um.loginUser(user)) {
			System.out.println("Login success!");
			logger.debug("Login success!");
			return "loginsuccess";
		} else {
			System.out.println("Login Error!");
			logger.debug("Login error");
			return "login";
		}

	}

}
