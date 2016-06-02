package com.dl.rmas.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.common.exception.SystemException;
import com.dl.rmas.common.utils.SecurityUtils;
import com.dl.rmas.dao.BaseDao;
import com.dl.rmas.entity.User;
import com.dl.rmas.entity.ValidityEntity;
import com.dl.rmas.service.BaseService;

public class BaseServiceImpl implements BaseService {

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public <T> T queryById(Class<T> type, Serializable id) {
		return baseDao.findById(type, id);
	}

	@Override
	public <T> List<T> queryAll(Class<T> entityClass) {
		return baseDao.findAll(entityClass);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> queryAllValid(Class<T> entityClass) {
		T t = null;
		try {
			t = entityClass.newInstance();
		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		} 
		
		ValidityEntity validityEntity = (ValidityEntity) t;
		validityEntity.setValidity(Validity.VALID);
		
		return queryByExample((T) validityEntity);
	}

	@Override
	public <T> List<T> queryByExample(T exampleEntity) {
		return baseDao.findByExample(exampleEntity);
	}

	@Override
	public Serializable doSave(Object entity) {
		return baseDao.save(entity);
	}

	@Override
	public void doUpdate(Object entity) {
		baseDao.update(entity);
	}

	@Override
	public void doSaveOrUpdate(Object entity) {
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void doDelete(Object entity) {
		baseDao.delete(entity);
	}

	@Override
	public void doDeleteById(Class<?> type, Serializable id) {
		baseDao.deleteById(type, id);
	}
	
	@Override
	public User currentUser() {
		return SecurityUtils.getCurrentUser();
	}

	@Override
	public Integer currentUserId() {
		if (currentUser() == null) {
			return null;
		}
		
		return currentUser().getUserId();
	}

	@Override
	public Date currentTime() {
		return new Date();
	}

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
}
