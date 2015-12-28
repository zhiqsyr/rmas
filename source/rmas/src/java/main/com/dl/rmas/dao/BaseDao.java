package com.dl.rmas.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class BaseDao {

	@Autowired
	protected HibernateTemplate hibernateTemplate;
	
	public <T> T findById(Class<T> type, Serializable id) {  
        return hibernateTemplate.get(type, id);  
    }  
	
	public <T> List<T> findAll(Class<T> entityClass) {
		return hibernateTemplate.loadAll(entityClass);
	}
	
	public <T> List<T> findByExample(T exampleEntity) {
		return hibernateTemplate.findByExample(exampleEntity);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T findByProperty(final Class<T> type, final String propertyName, final Object value) {

		return (T) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createCriteria(type)
						.add(Restrictions.eq(propertyName, value))
						.uniqueResult();
			}
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> findAllByProperty(final Class<T> type, final String propertyName, final Object value) {

		return (List<T>) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createCriteria(type)
						.add(Restrictions.eq(propertyName, value)).list();
			}
		});
	}
	
	public Serializable save(Object entity) {
		return hibernateTemplate.save(entity);
	}
	
	public void update(Object entity) {
		hibernateTemplate.update(entity);
	}
	
	public void saveOrUpdate(Object entity) {
		hibernateTemplate.saveOrUpdate(entity);
	}
	
	public void delete(Object entity) {
		if (entity != null) {
			hibernateTemplate.delete(entity);
		}
	}
	
	public void deleteById(Class<?> type, Serializable id) {  
        if (id == null) {  
            return;  
        }
        
        Object entity = findById(type, id);  
        if (entity == null) {  
            return;  
        }
        
        delete(entity);  
    }  

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}  
	
}
