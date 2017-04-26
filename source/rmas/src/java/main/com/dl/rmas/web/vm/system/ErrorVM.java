package com.dl.rmas.web.vm.system;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import com.dl.rmas.common.utils.SecurityUtils;

public class ErrorVM extends BaseVM {

	private static final Logger logger = Logger.getLogger(ErrorVM.class);
	
	@Wire
	private Window errorWin;
	
	private String errorMsg;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		Throwable th = (Throwable) Executions.getCurrent().getAttribute("javax.servlet.error.exception");

        logger.error("throwable exception:" + th.getClass().toString() + " : " + th.getMessage());

        logger.error("Main trace:" + getExceptionTrace(th));
        logger.error("Cause trace:" + getExceptionTrace(th.getCause()));
        logger.error("request URL:" + ((HttpServletRequest)Executions.getCurrent().getNativeRequest()).getRequestURL());
        logger.error("request referer URL:" + ((HttpServletRequest)Executions.getCurrent().getNativeRequest()).getHeader("referer"));

        th.printStackTrace();

        errorMsg = th.getCause().getMessage();
	}
	
	@Command
	public void onConfirm() {
		SecurityUtils.removeCurrentUser();
		errorWin.detach();
	}
	
	private String getExceptionTrace(Throwable th)
    {
        if (th == null)
            return null;

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        th.printStackTrace(pw);

        return sw.toString();
    }

	public String getErrorMsg() {
		return errorMsg;
	}
	
}
