package com.dl.rmas.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.entity.Menu;

@Repository
public class MenuDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public List<Menu> findValidMenusInSort() {
		StringBuffer hql = new StringBuffer();
		
		hql.append(" from Menu m ");
		hql.append(" where m.validity = 'VALID' ");
		hql.append(" order by ifnull(m.parentId, 0), m.menuId ");
		
		return (List<Menu>) hibernateTemplate.find(hql.toString());
	}
	
	@SuppressWarnings("unchecked")
	public List<Menu> findMenusByUserIdWithParent(Integer userId) {
		StringBuffer hql = new StringBuffer();
		List<Object> parmas = new ArrayList<Object>();
		
		hql.append(" from Menu m ");
		hql.append(" where exists ( ");
		hql.append(" 	select rm ");
		hql.append("    from RoleMenu rm ");
		hql.append("    where rm.menuId = m.menuId ");
		hql.append(" 	and exists ( ");
		hql.append("    	select ur ");
		hql.append("		from UserRole ur ");
		hql.append("		where ur.userId = ? and ur.roleId = rm.roleId ");
		parmas.add(userId);
		hql.append("		) ");
		hql.append("	) ");
		hql.append(" and m.validity = ? ");
		parmas.add(Validity.VALID);
		
		hql.append(" order by ifnull(m.parentId, 0), m.menuOrder ");
		
		List<Menu> leafs = (List<Menu>) hibernateTemplate.find(hql.toString(), parmas.toArray());
		
		if (CollectionUtils.isEmpty(leafs)) {
			return leafs;
		}
		
		List<Integer> parentIds = new ArrayList<Integer>();
		for (Menu menu : leafs) {
			if (!parentIds.contains(menu.getParentId())) {
				parentIds.add(menu.getParentId());
			}
		}
		Object[] parentIdArr = parentIds.toArray();
		
		// 获得关联的一级菜单列表，升序
		StringBuffer sb = new StringBuffer();
		sb.append(" from Menu m ");
		sb.append(" where m.menuId in ( ");
		for (int i = 0; i < parentIdArr.length; i ++) {
			if (i == parentIdArr.length - 1) {
				sb.append(parentIdArr[i]);
			} else {
				sb.append(parentIdArr[i] + ",");
			}
		}
		sb.append(" ) ");
		sb.append(" order by m.menuOrder ");
		
		// 首先加入一级菜单，然后下一级菜单
		List<Menu> result = new ArrayList<Menu>();
		result.addAll((Collection<? extends Menu>) hibernateTemplate.find(sb.toString()));
		result.addAll(leafs);
		return result;
	}
	
}
