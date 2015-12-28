package com.dl.rmas.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.dl.rmas.entity.OrderDo;
import com.dl.rmas.web.zkmodel.PagingDto;
import com.dl.rmas.web.zkmodel.Sorter;

@Repository
public class OrderDoDao extends BaseDao {

	/**
	 * 查询指定customerId的最大RMA编号；返回null表示没有记录
	 *
	 * @param customerId
	 * @return
	 */
	public String findMaxRmaByCustomerId(Integer customerId) {
		String hql = "select max(o.doRma) from OrderDo o where o.customerId = ?";
		
		List<?> result = hibernateTemplate.find(hql, customerId);
		if (result.size() == 1 && result.get(0) != null) {
			return result.get(0).toString();
		} 
		return null;
	}
	
	public List<OrderDo> findOrderDosByQueryDto(OrderDo queryDto, PagingDto pagingDto) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderDo.class);
		
		if (StringUtils.isNotBlank(queryDto.getRma())) {
			dc.add(Restrictions.sqlRestriction("exists (select null from t_order o where o.rma like ? and o.order_id = this_.order_id)", 
					"%" + queryDto.getRma() + "%",
					Hibernate.STRING));
		}
		if (StringUtils.isNotBlank(queryDto.getDoRma())) {
			dc.add(Restrictions.like("doRma", "%" + queryDto.getDoRma() + "%"));
		}
		if (queryDto.getDoTimeFrom() != null) {
			dc.add(Restrictions.ge("doTime", queryDto.getDoTimeFrom()));
		}
		if (queryDto.getDoTimeTo() != null) {
			dc.add(Restrictions.le("doTime", DateUtils.addDays(queryDto.getDoTimeTo(), 1)));
		}
		
		if (pagingDto == null) {
			dc.addOrder(org.hibernate.criterion.Order.desc("doRma"));
			return (List<OrderDo>) hibernateTemplate.findByCriteria(dc);
		} else {
			// 总数
			dc.setProjection(Projections.rowCount());
			pagingDto.setTotalSize((Long) hibernateTemplate.findByCriteria(dc).listIterator().next());
			
			// 排序
			if (CollectionUtils.isEmpty(pagingDto.getSorters())) {
				dc.addOrder(org.hibernate.criterion.Order.desc("doRma"));
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
			return (List<OrderDo>) hibernateTemplate.findByCriteria(dc, pagingDto.getActivePage() * pagingDto.getPageSize(), pagingDto.getPageSize());
		}
	}
	
}


