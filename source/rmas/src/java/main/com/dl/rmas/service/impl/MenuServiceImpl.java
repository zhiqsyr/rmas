package com.dl.rmas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dl.rmas.dao.MenuDao;
import com.dl.rmas.entity.Menu;
import com.dl.rmas.service.MenuService;

@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;
	
	@Override
	public List<Menu> queryAllValid() {
		return menuDao.findValidMenusInSort();
	}
	
	@Override
	public List<Menu> queryMenusByUserId(Integer userId) {
		if (userId == null) {
			return new ArrayList<Menu>();
		}
		
		return menuDao.findMenusByUserIdWithParent(userId);
	}
	
}
