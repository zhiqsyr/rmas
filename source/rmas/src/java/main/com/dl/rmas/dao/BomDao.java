package com.dl.rmas.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.dl.rmas.entity.Bom;
import com.dl.rmas.web.zkmodel.PagingDto;
import com.dl.rmas.web.zkmodel.Sorter;

@Repository
public class BomDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public List<Bom> findBomsByQueryDto(Bom queryDto, PagingDto pagingDto) {
		DetachedCriteria dc = DetachedCriteria.forClass(Bom.class);
		
		if (queryDto.getProductId() != null) {
			dc.add(Restrictions.eq("productId", queryDto.getProductId()));
		}
		if (StringUtils.isNotBlank(queryDto.getMaterialNo())) {
			dc.add(Restrictions.like("materialNo", "%" + queryDto.getMaterialNo() + "%"));
		}
		if (StringUtils.isNotBlank(queryDto.getMaterialName())) {
			dc.add(Restrictions.like("materialName", "%" + queryDto.getMaterialName() + "%"));
		}
		if (queryDto.getValidity() != null) {
			dc.add(Restrictions.eq("validity", queryDto.getValidity()));
		}		
		
		if (pagingDto == null) {
			dc.addOrder(org.hibernate.criterion.Order.desc("fullName"));
			return (List<Bom>) hibernateTemplate.findByCriteria(dc);
		} else {
			// 总数
			dc.setProjection(Projections.rowCount());
			pagingDto.setTotalSize((Long) hibernateTemplate.findByCriteria(dc).listIterator().next());
			
			// 排序
			if (CollectionUtils.isEmpty(pagingDto.getSorters())) {
				dc.addOrder(org.hibernate.criterion.Order.asc("sno"));
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
			return (List<Bom>) hibernateTemplate.findByCriteria(dc, pagingDto.getActivePage() * pagingDto.getPageSize(), pagingDto.getPageSize());
		}
	}
	
	/**
	 * 逻辑删除
	 *
	 * @param productId
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void deleteByProductId(final Integer productId) {
//		final String queryString = "delete Bom where productId = :productId";
		final String queryString = "update Bom set validity = 'INVALID' where productId = :productId";
		hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(queryString);
                query.setParameter("productId", productId);
                return query.executeUpdate();
            }
        });
	}
	
}
