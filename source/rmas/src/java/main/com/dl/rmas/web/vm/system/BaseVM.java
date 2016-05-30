package com.dl.rmas.web.vm.system;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.common.utils.SecurityUtils;
import com.dl.rmas.entity.User;

public class BaseVM {

	public static final String PARENT_WIN = "_parent_win";

	public User getCurrentUser() {
		return SecurityUtils.getCurrentUser();
	}
	
	public Window showModal(String url) {
		return showModal(url, null, new HashMap<String, Object>(), null, null, true, true, true, false);
    }
	
	public Window showModal(String url, Map<String, Object> arg) {
		return showModal(url, null, arg, null, null, true, true, true, false);
    }
	
	public Window showModal(String url, Component parent, Map<String, Object> arg) {
		return showModal(url, parent, arg, null, null, true, true, true, false);
    }
	
	public Window showModal(String url, Component parent, Map<String, Object> arg, String width, String height, boolean closeable, boolean sizeable, boolean maximizable,
		    boolean maximized) {
		// 父窗口对象放入PARENT_WIN的变量值里面
		Window win = (Window) Executions.createComponents(url, parent, arg);

		if (!StringUtils.isBlank(width)) {
		    win.setWidth(width);
		}

		if (!StringUtils.isBlank(height)) {
		    win.setHeight(height);
		}

		win.setClosable(closeable);
		win.setSizable(sizeable);
		win.setMaximizable(maximizable);
		win.setMaximized(maximized);
		
		win.doModal();
		return win;
    }
	
	@SuppressWarnings("unchecked")
    protected <T> T getArgValue(Class<T> type, String paramName) {
		Map<String, Object> arg = (Map<String, Object>) Executions.getCurrent().getArg();
		return arg == null ? null : (T) arg.get(paramName);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getRequestAttribute(Class<T> type, String paramName) {
    	return (T) Executions.getCurrent().getAttribute(paramName);
    }

    protected String getRequestParameter(String paramName) {
    	return Executions.getCurrent().getParameter(paramName);
    }

    protected String[] getRequestParameterValues(String paramName) {
    	return Executions.getCurrent().getParameterValues(paramName);
    }
	
	public void showInformationBox(String message) {
		try {
		    Messagebox.show(message, Constants.WEBAPP_NAME, Messagebox.OK, Messagebox.INFORMATION);
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
    }
	
    public void showErrorBox(String message) {
		try {
		    Messagebox.show(message, Constants.WEBAPP_NAME, Messagebox.OK, Messagebox.ERROR);
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
    }

    public void showWarningBox(String message) {
		try {
		    Messagebox.show(message, Constants.WEBAPP_NAME, Messagebox.OK, Messagebox.EXCLAMATION);
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
    }
    
    public boolean showQuestionBox(String message) {
		try {
		    return Messagebox.show(message, Constants.WEBAPP_NAME, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES;
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
    }
	
}
