package com.dl.rmas.service;

import java.util.List;

import com.dl.rmas.entity.Menu;

public interface MenuService extends BaseService {

	List<Menu> queryAllValid();
	
	List<Menu> queryMenusByUserId(Integer userId);
	
}
