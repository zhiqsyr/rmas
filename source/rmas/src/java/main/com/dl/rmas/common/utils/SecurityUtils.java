package com.dl.rmas.common.utils;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Sessions;

import com.dl.rmas.entity.User;

public class SecurityUtils {

	public static void setCurrentUser(User user) {
		Sessions.getCurrent().setAttribute("session-user", user);
	}
	
    public static User getCurrentUser() {
    	if (Sessions.getCurrent() != null) {
			return (User) Sessions.getCurrent().getAttribute("session-user");
		} else {
			return null;
		}
    } 
    
	/**
	 * <b>Function: <b>Servlet会话中获取当前用户
	 *
	 * @param httpSession Servlet会话，传空时尝试从ZK的会话中获取
	 * @return
	 */
    public static User getCurrentUser(HttpSession httpSession) {
    	User currentUser = null;
    	
    	if (httpSession != null) {
    		currentUser = (User) httpSession.getAttribute("session-user");
		} 
    	
		return currentUser == null ? getCurrentUser() : currentUser;
    } 	    
    
    public static Integer getCurrentUserId() {
    	User currentUser = getCurrentUser();
    	if (currentUser == null) {
			return null;
		} else {
			return currentUser.getUserId();
		}
    }
    
    public static void removeCurrentUser() {
    	if (Sessions.getCurrent() != null) {
    		Sessions.getCurrent().removeAttribute("session-user");
		}
    }
	
}
