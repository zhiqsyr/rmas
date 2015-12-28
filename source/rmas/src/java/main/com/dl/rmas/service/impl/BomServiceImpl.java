package com.dl.rmas.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.dao.BomDao;
import com.dl.rmas.entity.Bom;
import com.dl.rmas.service.BomService;
import com.dl.rmas.web.zkmodel.PagingDto;

@Service("bomService")
public class BomServiceImpl extends BaseServiceImpl implements BomService {

	@Autowired
	private BomDao bomDao;
	
	@Override
	public List<Bom> queryByProductId(Integer productId) {
		DetachedCriteria dc = DetachedCriteria.forClass(Bom.class);
		dc.add(Restrictions.eq("productId", productId));
		dc.add(Restrictions.eq("validity", Validity.VALID));
		dc.addOrder(Order.asc("materialName"));
		
		return (List<Bom>) bomDao.getHibernateTemplate().findByCriteria(dc);
	}
	
	@Override
	public List<Bom> queryBomsByQueryDto(Bom queryBom, PagingDto pagingDto) {
		return bomDao.findBomsByQueryDto(queryBom, pagingDto);
	}
	
	@Override
	public void doDeleteAllByProductId(Integer productId) {
		bomDao.deleteByProductId(productId);
	}
	
}
