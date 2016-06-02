package com.dl.rmas.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dl.rmas.common.utils.SecurityUtils;
import com.dl.rmas.entity.User;

public class UserAccessFilter implements Filter {
	private static final Log logger = LogFactory.getLog(UserAccessFilter.class);
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest hRequest = (HttpServletRequest) request;
		HttpSession session = hRequest.getSession(false);
		
		String requestUri = hRequest.getRequestURI(); 
		String requestName = requestUri.substring(requestUri.lastIndexOf("/") + 1);
		if (StringUtils.isBlank(requestName) || requestName.equals("user_login.zul")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// 会话为null，或者没有当前用户，跳转到登录界面
		User currentUser = SecurityUtils.getCurrentUser(session);
		if (session == null || currentUser == null) {
			request.getRequestDispatcher("/common/session_out.jsp").forward(request, response);
			return;
		}
		
		try {
			filterChain.doFilter(request, response);
		} catch (Throwable e) {
			SecurityUtils.removeCurrentUser();
			logger.error("request URL:" + ((HttpServletRequest) request).getRequestURL());
			logger.error(e.getMessage());
		} 
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
