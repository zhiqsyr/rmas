package com.dl.rmas.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.dl.rmas.entity.Product;
import com.dl.rmas.web.zkmodel.PagingDto;
import com.dl.rmas.web.zkmodel.Sorter;

@Repository
public class ProductDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public List<Product> findProductsByQueryDto(Product queryDto, PagingDto pagingDto) {
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		
		StringBuffer hql = new StringBuffer();
		List<Object> parmas = new ArrayList<Object>();
		
		hql.append(" select p ");
		hql.append(" from Product p where 1 = 1 ");
		if (StringUtils.isNotBlank(queryDto.getPn())) {
			hql.append(" and p.pn like ? ");
			parmas.add("%" + queryDto.getPn() + "%");
			
			dc.add(Restrictions.like("pn", "%" + queryDto.getPn() + "%"));
		}
		if (StringUtils.isNotBlank(queryDto.getBrand())) {
			hql.append(" and p.brand like ? ");
			parmas.add("%" + queryDto.getBrand() + "%");
			
			dc.add(Restrictions.like("brand", "%" + queryDto.getBrand() + "%"));
		}
		if (queryDto.getProductType() != null) {
			hql.append(" and p.productType = ? ");
			parmas.add(queryDto.getProductType());
			
			dc.add(Restrictions.eq("productType", queryDto.getProductType()));
		}
		if (StringUtils.isNotBlank(queryDto.getModelNo())) {
			hql.append(" and p.modelNo like ? ");
			parmas.add("%" + queryDto.getModelNo() + "%");
			
			dc.add(Restrictions.like("modelNo", "%" + queryDto.getModelNo() + "%"));
		}
		if (StringUtils.isNotBlank(queryDto.getPcbType())) {
			hql.append(" and p.pcbType like ? ");
			parmas.add("%" + queryDto.getPcbType() + "%");
			
			dc.add(Restrictions.like("pcbType", "%" + queryDto.getPcbType() + "%"));
		}
		if (queryDto.getValidity() != null) {
			dc.add(Restrictions.eq("validity", queryDto.getValidity()));
		}
		
		
//		hql.append(" order by ifnull(p.lastModifyTime, p.createTime) desc ");
//		
//		return (List<Product>) hibernateTemplate.find(hql.toString(), parmas.toArray());
		
		if (pagingDto == null) {
			dc.addOrder(org.hibernate.criterion.Order.desc("createTime"));
			return (List<Product>) hibernateTemplate.findByCriteria(dc);
		} else {
			// 总数
			dc.setProjection(Projections.rowCount());
			pagingDto.setTotalSize((Long) hibernateTemplate.findByCriteria(dc).listIterator().next());
			
			// 排序
			if (CollectionUtils.isEmpty(pagingDto.getSorters())) {
				dc.addOrder(org.hibernate.criterion.Order.desc("createTime"));
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
			return (List<Product>) hibernateTemplate.findByCriteria(dc, pagingDto.getActivePage() * pagingDto.getPageSize(), pagingDto.getPageSize());
		}
	}
	
}
