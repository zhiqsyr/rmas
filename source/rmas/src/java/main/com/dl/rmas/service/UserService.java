package com.dl.rmas.service;

import java.util.List;

import com.dl.rmas.entity.User;
import com.dl.rmas.web.zkmodel.PagingDto;

public interface UserService extends BaseService {

	boolean isLoginSuccess(User user);
	
	/**
	 * 获取当前登录用户
	 * 
	 * @param user
	 * @return
	 */
	User queryUserAtLogin(User user);
	
	List<User> queryAllSortedUsers();
	List<User> queryUsersByQueryDto(User queryDto, PagingDto pagingDto);
	
	boolean isUserNoExist(String userNo);
	
	/**
	 * 新建用户，密码使用MD5加密
	 * 
	 * @param user
	 */
	void doCreateUserWithMD5(User user);
	
	void doModifyUser(User user);
	/**
	 * 修改用户密码
	 * 
	 * @param user
	 * @param newPassword
	 */
	void doModifyUserPassword(User user, String newPassword);

}
