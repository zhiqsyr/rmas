package com.dl.rmas.web.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dl.rmas.common.utils.PropertiesUtils;
import com.dl.rmas.entity.User;
import com.dl.rmas.service.UserService;
import com.dl.rmas.web.vm.system.IndexVM;
import com.dl.rmas.web.vm.system.UserLoginVM;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	private UserService userService;
	
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init();
    	
    	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        userService = wac.getBean(UserService.class);
    }
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User login = new User();
		login.setUserNo(req.getParameter("userName"));
		login.setPassword(req.getParameter("password"));
		
		User currentUser = userService.queryUserAtLogin(login);
		if (currentUser == null) {
			req.setAttribute("userName", req.getParameter("userName"));
			req.setAttribute("password", req.getParameter("password"));
			req.setAttribute("failMessage", PropertiesUtils.getValueInSystem("login.servlet.fail.msg"));
			req.getRequestDispatcher(UserLoginVM.URL_USER_LOGIN).forward(req, resp);
			return;
		}
		
		req.getSession().setAttribute("session-user", currentUser);
		resp.sendRedirect(req.getContextPath() + IndexVM.URL_INDEX);
	}
	
}
