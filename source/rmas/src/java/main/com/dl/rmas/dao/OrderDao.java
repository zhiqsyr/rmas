package com.dl.rmas.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.dl.rmas.dto.OrderTrackDto;
import com.dl.rmas.entity.Order;
import com.dl.rmas.web.zkmodel.PagingDto;
import com.dl.rmas.web.zkmodel.Sorter;

@Repository
public class OrderDao extends BaseDao {

	/**
	 * 查询指定customerId的最大RMA编号；返回null表示没有记录
	 *
	 * @param customerId
	 * @return
	 */
	public String findMaxRmaByCustomerId(Integer customerId) {
		String hql = "select max(o.rma) from Order o where o.customerId = ?";
		
		List<?> result = hibernateTemplate.find(hql, customerId);
		if (result.size() == 1 && result.get(0) != null) {
			return result.get(0).toString();
		} 
		return null;
	}
	
	/**
	 * 
	 * 
	 * @param queryDto
	 * @param pagingDto	为null时，不作分页
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Order> findOrdersByQueryDto(Order queryDto, PagingDto pagingDto) {
		DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
		
		StringBuffer hql = new StringBuffer();
		List<Object> parmas = new ArrayList<Object>();
		
		hql.append(" select o ");
		hql.append(" from Order o where 1 = 1 ");
		if (StringUtils.isNotBlank(queryDto.getRma())) {
			hql.append(" and o.rma like ? ");
			parmas.add("%" + queryDto.getRma() + "%");
			
			dc.add(Restrictions.like("rma", "%" + queryDto.getRma() + "%"));
		}
		if (StringUtils.isNotBlank(queryDto.getCustrma())) {
			hql.append(" and o.custrma like ? ");
			parmas.add("%" + queryDto.getCustrma() + "%");
			
			dc.add(Restrictions.like("custrma", "%" + queryDto.getCustrma() + "%"));
		}
		if (queryDto.getCustomerId() != null) {
			hql.append(" and o.customerId = ? ");
			parmas.add(queryDto.getCustomerId());
			
			dc.add(Restrictions.eq("customerId", queryDto.getCustomerId()));
		}
		if (StringUtils.isNotBlank(queryDto.getExpressCom())) {
			hql.append(" and o.expressCom like ? ");
			parmas.add("%" + queryDto.getExpressCom() + "%");
			
			dc.add(Restrictions.like("expressCom", "%" + queryDto.getExpressCom() + "%"));
		}
		if (StringUtils.isNotBlank(queryDto.getExpressNo())) {
			hql.append(" and o.expressNo like ? ");
			parmas.add("%" + queryDto.getExpressNo() + "%");
			
			dc.add(Restrictions.like("expressNo", "%" + queryDto.getExpressNo() + "%"));
		}
		if (StringUtils.isNotBlank(queryDto.getReceiverName())) {
			hql.append(" and exists (select u from User u where u.userName like ? and u.userId = o.receiver) ");
			parmas.add("%" + queryDto.getReceiverName() + "%");
			
			dc.add(Restrictions.sqlRestriction("exists (select null from sm_user u where u.user_name like ? and u.user_id = receiver)", 
					"%" + queryDto.getReceiverName() + "%",
					Hibernate.STRING));
		}
		if (queryDto.getReceiveTimeFrom() != null) {
			hql.append(" and o.receiveTime >= ? ");
			parmas.add(queryDto.getReceiveTimeFrom());
			
			dc.add(Restrictions.gt("receiveTime", queryDto.getReceiveTimeFrom()));
		}
		if (queryDto.getReceiveTimeTo() != null) {
			hql.append(" and o.receiveTime <= ? ");
			parmas.add(DateUtils.addDays(queryDto.getReceiveTimeTo(), 1));
			
			dc.add(Restrictions.lt("receiveTime", DateUtils.addDays(queryDto.getReceiveTimeTo(), 1)));
		}
		if (queryDto.getCloseTimeFrom() != null) {
			dc.add(Restrictions.gt("closeTime", queryDto.getCloseTimeFrom()));
		}
		if (queryDto.getCloseTimeTo() != null) {
			dc.add(Restrictions.lt("closeTime", DateUtils.addDays(queryDto.getCloseTimeTo(), 1)));
		}		
		if (queryDto.getReceiveStatus() != null) {
			hql.append(" and o.receiveStatus = ? ");
			parmas.add(queryDto.getReceiveStatus());
			
			dc.add(Restrictions.eq("receiveStatus", queryDto.getReceiveStatus()));
		}
		if (queryDto.getKeyinStatus() != null) {
			hql.append(" and o.keyinStatus = ? ");
			parmas.add(queryDto.getKeyinStatus());
			
			dc.add(Restrictions.eq("keyinStatus", queryDto.getKeyinStatus()));
		}
		if (queryDto.getValidity() != null) {
			hql.append(" and o.validity = ? ");
			parmas.add(queryDto.getValidity());
			
			dc.add(Restrictions.eq("validity", queryDto.getValidity()));
		}
		
		if (pagingDto == null) {
			dc.addOrder(org.hibernate.criterion.Order.desc("receiveTime"));
			return (List<Order>) hibernateTemplate.findByCriteria(dc);
		} else {
			// 总数
			dc.setProjection(Projections.rowCount());
			pagingDto.setTotalSize((Long) hibernateTemplate.findByCriteria(dc).listIterator().next());
			
			// 排序
			if (CollectionUtils.isEmpty(pagingDto.getSorters())) {
				dc.addOrder(org.hibernate.criterion.Order.desc("receiveTime"));
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
			return (List<Order>) hibernateTemplate.findByCriteria(dc, pagingDto.getActivePage() * pagingDto.getPageSize(), pagingDto.getPageSize());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderTrackDto> findTrackByQueryDto(Order query,
			PagingDto pagingDto) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT o.order_id orderId, o.rma, o.custrma, o.close_time closeTime, ");
		sql.append(" 	o.receive_status receiveStatus, o.keyin_status keyinStatus, o.total_qty totalQty, ");
		sql.append(" 	sum(CASE s.status WHEN 'WAIT_MIDH' THEN 1 ELSE 0 END) waitMidhCount, ");
		sql.append(" 	sum(CASE s.status WHEN 'WAIT_FLASH' THEN 1 ELSE 0 END) waitFlashCount, ");
		sql.append(" 	sum(CASE s.status WHEN 'WAIT_L1KEYIN' THEN 1 ELSE 0 END) waitL1keyinCount, ");
		sql.append(" 	sum(CASE s.status WHEN 'WAIT_REPAIRING' THEN 1 ELSE 0 END) waitReparingCount, ");
		sql.append(" 	sum(CASE s.status WHEN 'WAIT_QC' THEN 1 ELSE 0 END) waitQcCount, ");
		sql.append(" 	sum(CASE s.status WHEN 'WAIT_DO' THEN 1 ELSE 0 END) waitDoCount, ");
		sql.append(" 	sum(CASE s.status WHEN 'DONE' THEN 1 ELSE 0 END) doneCount ");
		sql.append(" FROM t_sn s RIGHT JOIN t_order o ON s.order_id = o.order_id ");
		sql.append(" WHERE 1 = 1 ");
		if (query.getCustomerId() != null) {
			sql.append(" and o.customer_id = " + query.getCustomerId());
		}
		if (StringUtils.isNotBlank(query.getCustrma())) {
			sql.append(" and o.custrma like '%" + query.getCustrma() + "%' ");
		}
		if (StringUtils.isNotBlank(query.getRma())) {
			sql.append(" and o.rma like '%" + query.getRma() + "%' ");
		}		
		if (query.getCloseTimeFrom() != null) {
			sql.append(" and o.close_time >= :from ");
		}
		if (query.getCloseTimeTo() != null) {
			sql.append(" and o.close_time <= :to ");
		}
		if (query.getDoStatus() != null) {
			sql.append(" and o.do_status = '" + query.getDoStatus().name() + "' ");
		}
		sql.append(" GROUP BY o.order_id, o.rma, o.custrma, o.close_time, o.receive_status, o.keyin_status ");
		sql.append(" order by o.rma desc ");
	
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		
		if (pagingDto == null) {
			SQLQuery q = session.createSQLQuery(sql.toString());
			if (query.getCloseTimeFrom() != null) {
				q.setParameter("from", query.getCloseTimeFrom());
			}
			if (query.getCloseTimeTo() != null) {
				q.setParameter("to", DateUtils.addDays(query.getCloseTimeTo(), 1));
			}
			q.setResultTransformer(Transformers.aliasToBean(OrderTrackDto.class));
			return q.list();			
		}
		
		// 查询总数
		String countSql = "select count(distinct o.order_id) " + sql.substring(sql.indexOf("FROM"), sql.indexOf("GROUP BY"));
		SQLQuery count = session.createSQLQuery(countSql);
		if (query.getCloseTimeFrom() != null) {
			count.setParameter("from", query.getCloseTimeFrom());
		}
		if (query.getCloseTimeTo() != null) {
			count.setParameter("to", DateUtils.addDays(query.getCloseTimeTo(), 1));
		}
		pagingDto.setTotalSize(Long.parseLong(count.list().get(0).toString()));
		
		// 查询当前分页数据
		SQLQuery q = session.createSQLQuery(sql.toString());
//		q.addScalar("waitMidhCount", Hibernate.INTEGER);
		if (query.getCloseTimeFrom() != null) {
			q.setParameter("from", query.getCloseTimeFrom());
		}
		if (query.getCloseTimeTo() != null) {
			q.setParameter("to", DateUtils.addDays(query.getCloseTimeTo(), 1));
		}
		q.setResultTransformer(Transformers.aliasToBean(OrderTrackDto.class));
		q.setFirstResult(pagingDto.getActivePage() * pagingDto.getPageSize());
		q.setMaxResults(pagingDto.getPageSize());
		return q.list();
	}
	
}


