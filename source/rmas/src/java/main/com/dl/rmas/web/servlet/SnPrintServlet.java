package com.dl.rmas.web.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dl.rmas.entity.Sn;
import com.dl.rmas.service.SnService;

@SuppressWarnings("serial")
public class SnPrintServlet extends HttpServlet {

	private SnService snService;
	
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init();
    	
    	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
    	snService = wac.getBean(SnService.class);
    }
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer snId = Integer.parseInt(req.getParameter("snId"));
		Sn sn = snService.queryById(Sn.class, snId);
		
		req.setAttribute("sn", sn);
		req.getRequestDispatcher("/jsp/sn_print.jsp").forward(req, resp);
	}
	
}
