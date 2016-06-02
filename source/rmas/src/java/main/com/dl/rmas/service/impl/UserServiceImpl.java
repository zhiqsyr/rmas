package com.dl.rmas.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.dao.UserDao;
import com.dl.rmas.entity.User;
import com.dl.rmas.service.UserService;
import com.dl.rmas.web.zkmodel.PagingDto;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean isLoginSuccess(User user) {
		List<User> result = queryByExample(user);
		
		if (result.size() == 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public User queryUserAtLogin(User user) {
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		
		List<User> result = queryByExample(user);
		if (result.size() == 1 && result.get(0).getUserNo().equals(user.getUserNo())) {
			return result.get(0);
		}
		return null;
	}
	
	@Override
	public List<User> queryAllSortedUsers() {
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		
		dc.addOrder(Order.asc("userName"));
		
		return (List<User>) userDao.getHibernateTemplate().findByCriteria(dc);
	}	

	@Override
	public List<User> queryUsersByQueryDto(User queryDto, PagingDto pagingDto) {
		return userDao.findUsersByQueryDto(queryDto, pagingDto);
	}
	
	@Override
	public boolean isUserNoExist(String userNo) {
		User user = new User();
		user.setUserNo(userNo);
		
		List<User> result = queryByExample(user);
		if (result.size() == 1 && result.get(0).getUserNo().equals(userNo)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void doCreateUserWithMD5(User user) {
		Assert.notNull(user);
		
		// MD5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		user.setCreateUser(currentUserId());
		user.setCreateTime(currentTime());
		user.setValidity(Validity.VALID);
	
		doSave(user);
	}
	
	@Override
	public void doModifyUser(User user) {
		Assert.notNull(user);
		
		user.setLastModifier(currentUserId());
		user.setLastModifyTime(currentTime());
		
		doUpdate(user);
	}
	
	@Override
	public void doModifyUserPassword(User user, String newPassword) {
		// MD5加密
		user.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
		
		doModifyUser(user);
	}
	
}
