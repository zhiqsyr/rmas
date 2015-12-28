package com.dl.rmas.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.dl.rmas.entity.User;
import com.dl.rmas.web.zkmodel.PagingDto;
import com.dl.rmas.web.zkmodel.Sorter;

@Repository
public class UserDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public List<User> findUsersByQueryDto(User queryDto, PagingDto pagingDto) {
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		hql.append(" from User u ");
		hql.append(" where 1 = 1 ");
		if (queryDto != null) {
			if (StringUtils.isNotBlank(queryDto.getUserName())) {
				hql.append(" and u.userName like ? ");
				params.add("%" + queryDto.getUserName() + "%");
			
				dc.add(Restrictions.like("userName", "%" + queryDto.getUserName() + "%"));
			}
			if (StringUtils.isNotBlank(queryDto.getDeptName())) {
				hql.append(" and u.deptName like ? ");
				params.add("%" + queryDto.getDeptName() + "%");
				
				dc.add(Restrictions.like("deptName", "%" + queryDto.getDeptName() + "%"));
			}
			if (StringUtils.isNotBlank(queryDto.getPost())) {
				hql.append(" and u.post like ? ");
				params.add("%" + queryDto.getPost() + "%");
				
				dc.add(Restrictions.like("post", "%" + queryDto.getPost() + "%"));
			}
			if (StringUtils.isNotBlank(queryDto.getCareerLevel())) {
				hql.append(" and u.careerLevel like ? ");
				params.add("%" + queryDto.getCareerLevel() + "%");
				
				dc.add(Restrictions.like("careerLevel", "%" + queryDto.getCareerLevel() + "%"));
			}
			if (StringUtils.isNotBlank(queryDto.getRoleName())) {
				hql.append(" and exists (select r ");
				hql.append("		from Role r ");
				hql.append("		where r.roleName like ? ");
				hql.append("		and exists (select ur ");
				hql.append("			from UserRole ur ");
				hql.append("			where ur.userId = u.userId ");
				hql.append("			and ur.roleId = r.roleId ");
				hql.append("		) ");
				hql.append(" ) ");
				
				dc.add(Restrictions.sqlRestriction("exists (select null from sm_role r where r.role_name like ? " +
						"and exists (select null from sm_user_role ur where ur.user_id = this_.user_id and ur.role_id = r.role_id)) ", 
						"%" + queryDto.getRoleName() + "%",
						Hibernate.STRING));
			}
			if (queryDto.getValidity() != null) {
				hql.append(" and u.validity = ? ");
				params.add(queryDto.getValidity());
				
				dc.add(Restrictions.eq("validity", queryDto.getValidity()));
			}
		}
		
//		hql.append(" order by u.createTime desc ");
//		
//		return (List<User>) hibernateTemplate.find(hql.toString(), params.toArray());
		
		if (pagingDto == null) {
			dc.addOrder(org.hibernate.criterion.Order.desc("userNo"));
			return (List<User>) hibernateTemplate.findByCriteria(dc);
		} else {
			// 总数
			dc.setProjection(Projections.rowCount());
			pagingDto.setTotalSize((Long) hibernateTemplate.findByCriteria(dc).listIterator().next());
			
			// 排序
			if (CollectionUtils.isEmpty(pagingDto.getSorters())) {
				dc.addOrder(org.hibernate.criterion.Order.desc("userNo"));
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
			return (List<User>) hibernateTemplate.findByCriteria(dc, pagingDto.getActivePage() * pagingDto.getPageSize(), pagingDto.getPageSize());
		}
	}
	
}
