package com.springmvclearn.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.springmvclearn.model.User;

public class Authority implements HandlerInterceptor {
	private Logger logger = Logger.getLogger(Authority.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession s = request.getSession(); //Check if the sess's exists
		User u = (User) s.getAttribute("User");
logger.debug("The login.jsp's intercepted.....");
System.out.println("The user in sesion is:" + u);
		if (null == u){
			request.getRequestDispatcher("/WEB-INF/views/login/login.jsp").forward(request, response);
		} else{
			return true;
		}
		
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
