package com.dl.core.jxls.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dl.core.jxls.dao.ColumnConfigDao;
import com.dl.core.jxls.dao.ReportConfigDao;
import com.dl.core.jxls.entity.ColumnConfig;
import com.dl.core.jxls.entity.ReportConfig;

/**
 * 
 * @author dylan
 * @date 2013-5-14 上午10:27:01
 */
@Service("reportConfigService")
public class ReportConfigService {

	@Autowired
	private ColumnConfigDao columnConfigDao;
	@Autowired
	private ReportConfigDao reportConfigDao;

	public ReportConfig findByReportType(String reportType) {
		return reportConfigDao.findByProperty(ReportConfig.class, "reportType", reportType);
	}
	
	public ColumnConfig findByReportTypeAndName(String reportType,
			String columnName) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(ColumnConfig.class);
		criteria.createAlias("reportConfig", "reportConfig");
		criteria.add(Restrictions.eq("reportConfig.reportType", reportType));
		criteria.add(Restrictions.eq("columnName", columnName));
		List<ColumnConfig> result = (List<ColumnConfig>) reportConfigDao.getHibernateTemplate().findByCriteria(criteria);
		return result == null || result.size() < 1 ? null : result.get(0);
	}

	public ColumnConfigDao getColumnConfigDao() {
		return columnConfigDao;
	}

	public ReportConfigDao getReportConfigDao() {
		return reportConfigDao;
	}
	
}
