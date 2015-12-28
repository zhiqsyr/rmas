package com.dl.rmas.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.dl.rmas.entity.Customer;
import com.dl.rmas.web.zkmodel.PagingDto;
import com.dl.rmas.web.zkmodel.Sorter;

@Repository
public class CustomerDao extends BaseDao {

	
	@SuppressWarnings("unchecked")
	public List<Customer> findCustomersByQueryDto(Customer queryDto, PagingDto pagingDto) {
		DetachedCriteria dc = DetachedCriteria.forClass(Customer.class);
		
		StringBuffer hql = new StringBuffer();
		List<Object> parmas = new ArrayList<Object>();
		
		hql.append(" select o ");
		hql.append(" from Customer o where 1 = 1 ");
		if (StringUtils.isNotBlank(queryDto.getFullName())) {
			hql.append(" and o.fullName like ? ");
			parmas.add("%" + queryDto.getFullName() + "%");
		
			dc.add(Restrictions.like("fullName", "%" + queryDto.getFullName() + "%"));
		}
		if (StringUtils.isNotBlank(queryDto.getShortName())) {
			hql.append(" and o.shortName like ? ");
			parmas.add("%" + queryDto.getShortName() + "%");
			
			dc.add(Restrictions.like("shortName", "%" + queryDto.getShortName() + "%"));
		}
		if (queryDto.getValidity() != null) {
			hql.append(" and o.validity = ? ");
			parmas.add(queryDto.getValidity());
			
			dc.add(Restrictions.eq("validity", queryDto.getValidity()));
		}
//		hql.append(" order by o.customerId desc ");
//		
//		return (List<Customer>) hibernateTemplate.find(hql.toString(), parmas.toArray());
		
		if (pagingDto == null) {
			dc.addOrder(org.hibernate.criterion.Order.desc("fullName"));
			return (List<Customer>) hibernateTemplate.findByCriteria(dc);
		} else {
			// 总数
			dc.setProjection(Projections.rowCount());
			pagingDto.setTotalSize((Long) hibernateTemplate.findByCriteria(dc).listIterator().next());
			
			// 排序
			if (CollectionUtils.isEmpty(pagingDto.getSorters())) {
				dc.addOrder(org.hibernate.criterion.Order.desc("fullName"));
			} else {
				for (Sorter sorter : pagingDto.getSorters()) {
					if (sorter.isAscending()) {
						dc.addOrder(org.hibernate.criterion.Order.asc(sorter.getPropertyName()));
					} else {
						dc.addOrder(org.hibernate.criterion.Order.desc(sorter.getPropertyName()));
					}
				}
			}
			dc.setProjection(null);
			return (List<Customer>) hibernateTemplate.findByCriteria(dc, pagingDto.getActivePage() * pagingDto.getPageSize(), pagingDto.getPageSize());
		}
	}
	
}


