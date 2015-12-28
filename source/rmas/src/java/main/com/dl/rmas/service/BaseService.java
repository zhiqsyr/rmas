package com.dl.rmas.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dl.rmas.entity.User;

public interface BaseService {

	<T> T queryById(Class<T> type, Serializable id);
	
	<T> List<T> queryAll(Class<T> entityClass);
	
	<T> List<T> queryAllValid(Class<T> entityClass);
	
	<T> List<T> queryByExample(T exampleEntity);
	
	Serializable doSave(Object entity);
	
	void doUpdate(Object entity);
	
	void doSaveOrUpdate(Object entity);
	
	void doDelete(Object entity);
	
	void doDeleteById(Class<?> type, Serializable id);
	
	User currentUser();
	
	Integer currentUserId();
	
	Date currentTime();
	
}
