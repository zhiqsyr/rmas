package com.dl.rmas.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.dl.rmas.common.enums.FinalResult;
import com.dl.rmas.common.enums.SnStatus;
import com.dl.rmas.dto.DODateDto;
import com.dl.rmas.dto.FinalResultDto;
import com.dl.rmas.dto.NumberDto;
import com.dl.rmas.dto.ProduceDto;
import com.dl.rmas.dto.RepairingDto;
import com.dl.rmas.dto.SnDto;
import com.dl.rmas.dto.StopReasonDto;
import com.dl.rmas.entity.Order.RmaDoStatus;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.web.zkmodel.PagingDto;
import com.dl.rmas.web.zkmodel.Sorter;

@Repository
public class SnDao extends BaseDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Integer findSurplusUndoCount(Integer orderId) {
		String hql = "SELECT count(*) FROM Sn s WHERE s.orderId = ? AND s.status != 'DONE'";
		
		List<?> result = hibernateTemplate.find(hql, orderId);
		if (result.size() == 1 && result.get(0) != null) {
			return Integer.parseInt(result.get(0).toString());
		} 
		return null;		
	}	

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Sn> findDtosByQueryDto(SnDto queryDto, PagingDto pagingDto) {
		DetachedCriteria dc = DetachedCriteria.forClass(Sn.class);
		
		dc.createAlias("order", "order");
		if (queryDto.getCustomerId() != null) {
			dc.add(Restrictions.eq("order.customerId", queryDto.getCustomerId()));
		}
		if (queryDto.getOrderId() != null) {
			dc.add(Restrictions.eq("orderId", queryDto.getOrderId()));
		}
		if (StringUtils.isNotBlank(queryDto.getRma())) {
			dc.add(Restrictions.like("order.rma", "%" + queryDto.getRma() + "%"));
		}
		if (StringUtils.isNotBlank(queryDto.getCustrma())) {
			dc.add(Restrictions.like("order.custrma", "%" + queryDto.getCustrma() + "%"));
		}
		if (queryDto.getSnIndex() != null) {
			dc.add(Restrictions.eq("snIndex", queryDto.getSnIndex()));
		}
		if (StringUtils.isNotBlank(queryDto.getPn())) {
			dc.add(Restrictions.sqlRestriction("exists (select null from sm_product p where p.pn like ? and p.product_id = this_.product_id)", 
					"%" + queryDto.getPn() + "%",
					Hibernate.STRING));
		}
		if (StringUtils.isNotBlank(queryDto.getSn())) {
			dc.add(Restrictions.like("sn", "%" + queryDto.getSn() + "%"));
		}
		if (queryDto.getReceiveStatus() != null) {
			dc.add(Restrictions.eq("order.receiveStatus", queryDto.getReceiveStatus()));
		}
		if (queryDto.getKeyinStatus() != null) {
			dc.add(Restrictions.eq("order.keyinStatus", queryDto.getKeyinStatus()));
		}
		if (queryDto.getKeepStatus() != null) {
			dc.add(Restrictions.eq("keepStatus", queryDto.getKeepStatus()));
		}
		if (queryDto.getStatus() != null) {
			dc.add(Restrictions.eq("status", queryDto.getStatus()));
		}
		if (queryDto.getSnDoStatus() != null) {
			if (RmaDoStatus.ONLINE.equals(queryDto.getSnDoStatus())) {
				dc.add(Restrictions.ne("status", SnStatus.DONE));
			} else {
				dc.add(Restrictions.eq("status", SnStatus.DONE));
			}
		}
		if (queryDto.getRepairer() != null) {
			dc.add(Restrictions.eq("repairer", queryDto.getRepairer()));
		}
		if (StringUtils.isNotBlank(queryDto.getRepairerName())) {
			dc.add(Restrictions.sqlRestriction("exists (select null from sm_user u where u.user_name like ? and this_.repairer = u.user_id)", 
					"%" + queryDto.getRepairerName() + "%",
					Hibernate.STRING));
		}
		if (StringUtils.isNotBlank(queryDto.getMacImei1())) {
			dc.add(Restrictions.like("macImei1", "%" + queryDto.getMacImei1() + "%"));
		}
		if (StringUtils.isNotBlank(queryDto.getMacImei1N())) {
			dc.add(Restrictions.like("macImei1N", "%" + queryDto.getMacImei1N() + "%"));
		}		
		if (StringUtils.isNotBlank(queryDto.getMacImei2())) {
			dc.add(Restrictions.like("macImei2", "%" + queryDto.getMacImei2() + "%"));
		}
		if (queryDto.getReceiveDateFrom() != null) {
			dc.add(Restrictions.gt("order.receiveTime", queryDto.getReceiveDateFrom()));
		}
		if (queryDto.getReceiveDateTo() != null) {
			dc.add(Restrictions.lt("order.receiveTime", DateUtils.addDays(queryDto.getReceiveDateTo(), 1)));
		}
		if (queryDto.getCloseDateFrom() != null) {
			dc.add(Restrictions.gt("order.closeTime", queryDto.getCloseDateFrom()));
		}
		if (queryDto.getCloseDateTo() != null) {
			dc.add(Restrictions.lt("order.closeTime", DateUtils.addDays(queryDto.getCloseDateTo(), 1)));
		}
		if (queryDto.getHardLevel() != null) {
			dc.add(Restrictions.eq("hardLevel", queryDto.getHardLevel()));
		}
		if (StringUtils.isNotBlank(queryDto.getCreateUserName())) {
			dc.add(Restrictions.sqlRestriction("exists (select null from sm_user u where u.user_name like ? and this_.create_user = u.user_id)", 
					"%" + queryDto.getCreateUserName() + "%",
					Hibernate.STRING));
		}
		if (queryDto.getCreateTimeFrom() != null) {
			dc.add(Restrictions.gt("createTime", queryDto.getCreateTimeFrom()));
		}
		if (queryDto.getCreateTimeTo() != null) {
			dc.add(Restrictions.lt("createTime", DateUtils.addDays(queryDto.getCreateTimeTo(), 1)));
		}
		if (queryDto.getDoTimeFrom() != null) {
			dc.add(Restrictions.gt("doTime", queryDto.getDoTimeFrom()));
		}
		if (queryDto.getDoTimeEnd() != null) {
			dc.add(Restrictions.lt("doTime", DateUtils.addDays(queryDto.getDoTimeEnd(), 1)));
		}
		if (queryDto.getFinalResult() != null) {
			dc.add(Restrictions.eq("finalResult", queryDto.getFinalResult()));
		}
		if (queryDto.getOqcResult() != null) {
			dc.add(Restrictions.sqlRestriction("(this_.oqc_result is null or this_.oqc_result = 'NG')"));
		}
		
		if (pagingDto == null) {
			dc.addOrder(org.hibernate.criterion.Order.desc("createTime"));
			return (List<Sn>) hibernateTemplate.findByCriteria(dc);
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
			dc.setResultTransformer(CriteriaSpecification.ROOT_ENTITY); 
			return (List<Sn>) hibernateTemplate.findByCriteria(dc, pagingDto.getActivePage() * pagingDto.getPageSize(), pagingDto.getPageSize());
		}
	}
	
	public List<ProduceDto> findProduceDtosByQueryDto(SnDto queryDto) {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		
		sql.append("SELECT entry.user_name userName, o.custrma, o.rma, s.sn_index snIndex, s.sn, p.pn, p.pcb_type pcbType, dc.code_key productType, ");
		sql.append("	o.keyin_status keyinStatus, s.hard_level hardLevel, date_format(o.receive_time, '%Y-%m-%d') receiveTime,");
		sql.append("	date_format(s.create_time, '%Y-%m-%d') createTime, date_format(s.assign_time, '%Y-%m-%d') assignTime,");
		sql.append("	date_format(s.repaired_time, '%Y-%m-%d') repairedTime, date_format(s.qc_time, '%Y-%m-%d') qcTime,");
		sql.append("	date_format(s.do_time, '%Y-%m-%d') doTime, date_format(o.close_time, '%Y-%m-%d') closeTime,");
		sql.append("	s.status, flasher.user_name flasherName, flasher.user_no flasherNo, s.flash_result flashResult,");
		sql.append("	repairer.user_name repairerName, repairer.user_no repairerNo, code.code_name repairCode,");
		sql.append("	s.repair_remark repairRemark, s.repair_result repairResult, s.material_used materialUsed,");
		sql.append("	b.material_name partName, b.category cate, b.item_cate itemCate, b.material_no materialNo, b.ino ino, rm.number usedAmount, qcer.user_name qcerName, qcer.user_no qcerNo,");
		sql.append("	s.qc_result qcResult, stoper.user_name stoperName, stoper.user_no stoperNo, s.stop_reason stopReason,");
		sql.append("	s.final_result finalResult, s.keep_status keepStatus, s.mac_imei1 macImei1, s.mac_imei1_n macImei1N,");
		sql.append("	s.mac_imei2 macImei2, s.mac_imei2_n macImei2N, ");
		sql.append("	cidType.code_name cidTypeFormatted, cfd.code_name customerFaultDescFormatted, ");
		sql.append("	ot.code_name outletFormatted, s.fail_code failCode, s.case_id caseId, s.remark ");
		sql.append("FROM t_sn s ");
		sql.append("JOIN sm_user entry ON s.create_user = entry.user_id ");
		sql.append("JOIN t_order o ON s.order_id = o.order_id ");
		sql.append("JOIN sm_product p ON s.product_id = p.product_id ");
		sql.append("LEFT JOIN sm_dict_code dc ON p.product_type = dc.code_id ");
		sql.append("LEFT JOIN sm_user flasher ON s.flasher = flasher.user_id ");
		sql.append("LEFT JOIN sm_user repairer ON s.repairer = repairer.user_id ");
		sql.append("LEFT JOIN sm_user qcer ON s.qcer = qcer.user_id ");
		sql.append("LEFT JOIN sm_user stoper ON s.stoper = stoper.user_id ");
		sql.append("LEFT JOIN sm_dict_code code ON s.repair_code = code.code_id ");
		sql.append("LEFT JOIN sm_dict_code cidType ON s.cid_type = cidType.code_id ");
		sql.append("LEFT JOIN sm_dict_code cfd ON s.customer_fault_desc = cfd.code_id ");
		sql.append("LEFT JOIN sm_dict_code ot ON s.outlet = ot.code_id ");
		sql.append("LEFT JOIN t_sn_repair_material rm ON s.sn_id = rm.sn_id ");
		sql.append("LEFT JOIN sm_bom b ON rm.bom_id = b.bom_id ");
		sql.append("WHERE 1 = 1 ");
		if (queryDto.getCustomerId() != null) {
			sql.append(" and o.customer_id = ? ");
			params.add(queryDto.getCustomerId());
		}
		if (queryDto.getOrderId() != null) {
			sql.append(" and s.order_id = ? ");
			params.add(queryDto.getOrderId());
		}
		if (StringUtils.isNotBlank(queryDto.getRma())) {
			sql.append(" and o.rma like ? ");
			params.add("%" + queryDto.getRma() + "%");
		}
		if (StringUtils.isNotBlank(queryDto.getCustrma())) {
			sql.append(" and o.custrma like ? ");
			params.add("%" + queryDto.getCustrma() + "%");
		}
		if (queryDto.getSnIndex() != null) {
			sql.append(" and s.sn_index = ? ");
			params.add(queryDto.getSnIndex());			
		}
		if (StringUtils.isNotBlank(queryDto.getPn())) {
			sql.append(" and p.pn like ? ");
			params.add("%" + queryDto.getPn() + "%");
		}
		if (StringUtils.isNotBlank(queryDto.getSn())) {
			sql.append(" and s.sn like ? ");
			params.add("%" + queryDto.getSn() + "%");
		}
		if (queryDto.getReceiveStatus() != null) {
			sql.append(" and o.receive_status = ? ");
			params.add(queryDto.getReceiveStatus().name());
		}
		if (queryDto.getKeyinStatus() != null) {
			sql.append(" and o.keyin_status = ? ");
			params.add(queryDto.getKeyinStatus().name());
		}
		if (queryDto.getKeepStatus() != null) {
			sql.append(" and s.keep_status = ? ");
			params.add(queryDto.getKeepStatus().name());
		}
		if (queryDto.getStatus() != null) {
			sql.append(" and s.status = ? ");
			params.add(queryDto.getStatus().name());
		}
		if (queryDto.getSnDoStatus() != null) {
			if (RmaDoStatus.ONLINE.equals(queryDto.getSnDoStatus())) {
				sql.append(" and s.status != ? ");
				params.add(SnStatus.DONE.name());
			} else {
				sql.append(" and s.status = ? ");
				params.add(SnStatus.DONE.name());
			}
		}		
		if (queryDto.getRepairer() != null) {
			sql.append(" and s.repairer = ? ");
			params.add(queryDto.getRepairer());
		}
		if (StringUtils.isNotBlank(queryDto.getRepairerName())) {
			sql.append(" and repairer.user_name like ? ");
			params.add("%" + queryDto.getRepairerName() + "%");
		}
		if (StringUtils.isNotBlank(queryDto.getMacImei1())) {
			sql.append(" and s.mac_imei1 = ? ");
			params.add("%" + queryDto.getMacImei1() + "%");
		}
		if (StringUtils.isNotBlank(queryDto.getMacImei1N())) {
			sql.append(" and s.mac_imei1_n = ? ");
			params.add("%" + queryDto.getMacImei1N() + "%");
		}		
		if (StringUtils.isNotBlank(queryDto.getMacImei2())) {
			sql.append(" and s.mac_imei2 = ? ");
			params.add("%" + queryDto.getMacImei2() + "%");
		}
		if (queryDto.getReceiveDateFrom() != null) {
			sql.append(" and o.receive_time >= ? ");
			params.add(new Timestamp(queryDto.getReceiveDateFrom().getTime()));
		}
		if (queryDto.getReceiveDateTo() != null) {
			sql.append(" and o.receive_time <= ? ");
			params.add(new Timestamp(DateUtils.addDays(queryDto.getReceiveDateTo(), 1).getTime()));
		}
		if (queryDto.getCloseDateFrom() != null) {
			sql.append(" and o.close_time >= ? ");
			params.add(new Timestamp(queryDto.getCloseDateFrom().getTime()));
		}
		if (queryDto.getCloseDateTo() != null) {
			sql.append(" and o.close_time <= ? ");
			params.add(new Timestamp(DateUtils.addDays(queryDto.getCloseDateTo(), 1).getTime()));
		}
		if (queryDto.getHardLevel() != null) {
			sql.append(" and s.hard_level = ? ");
			params.add(queryDto.getHardLevel());
		}
		if (StringUtils.isNotBlank(queryDto.getCreateUserName())) {
			sql.append(" and entry.user_name like ? ");
			params.add("%" + queryDto.getCreateUserName() + "%");
		}
		if (queryDto.getCreateTimeFrom() != null) {
			sql.append(" and s.create_time >= ? ");
			params.add(new Timestamp(queryDto.getCreateTimeFrom().getTime()));
		}
		if (queryDto.getCreateTimeTo() != null) {
			sql.append(" and s.create_time <= ? ");
			params.add(new Timestamp(DateUtils.addDays(queryDto.getCreateTimeTo(), 1).getTime()));
		}
		if (queryDto.getDoTimeFrom() != null) {
			sql.append(" and s.do_time >= ? ");
			params.add(new Timestamp(queryDto.getDoTimeFrom().getTime()));
		}
		if (queryDto.getDoTimeEnd() != null) {
			sql.append(" and s.do_time <= ? ");
			params.add(new Timestamp(DateUtils.addDays(queryDto.getDoTimeEnd(), 1).getTime()));
		}		
		if (queryDto.getFinalResult() != null) {
			sql.append(" and s.final_result = ? ");
			params.add(queryDto.getFinalResult().name());
		}
		sql.append("ORDER BY o.rma DESC, s.sn_index ");

		return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper(ProduceDto.class));
	}
	
	/**
	 * 员工在修产品数量
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RepairingDto> findUserRepairingCount() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT u.user_no as userNo, u.user_name as userName, count(*) repairingCount ");
		sql.append(" FROM t_sn t JOIN sm_user u ON t.repairer = u.user_id ");
		sql.append(" WHERE t.status IN ('WAIT_FLASH', 'WAIT_REPAIRING') ");
		sql.append(" GROUP BY t.repairer ORDER BY repairingCount ");
	
		Query q = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString())
					.setResultTransformer(Transformers.aliasToBean(RepairingDto.class));
		
		return q.list();
	}
	
	
	/**
	 * 查询指定sn的doresult状态 
	 * 
	 * @param sn
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findFinalresult(String sn){
		String sql = "SELECT s.final_result as finalResult FROM t_sn s WHERE s.sn = '"+ sn +"' order by s.sn_id desc";
		Query q = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.aliasToBean(FinalResultDto.class));
		List<FinalResultDto> result = q.list();
		if (result != null) {
			return result.get(0).getFinalResult();
			
		}
		return null;
	}
	
	/**
	 * 查询指定sn的出货时间
	 * 
	 * @param sn
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Timestamp findDODate(String sn) {
		String sql = "SELECT s.do_time as doDate FROM t_sn s WHERE s.sn = '"+ sn +"' order by s.sn_id desc";
		Query q = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.aliasToBean(DODateDto.class));
		List<DODateDto> result = q.list();
		if (result != null) {
			return result.get(0).getDoDate();
			
		}
		return null;	
		
	}
	
	/**
	 * 查询指定sn的StopReason
	 * 
	 * @param sn
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findStopReason(String sn) {
		String sql = "SELECT s.stop_reason as stopReason FROM t_sn s WHERE s.sn = '"+ sn +"' order by s.sn_id desc";
		Query q = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.aliasToBean(StopReasonDto.class));
		List<StopReasonDto> result = q.list();
		if (result != null) {
			return result.get(0).getStopReason();
			
		}
		return null;
	}
	
	
	
	
	
	/**
	 * 查询指定sn的二反次数
	 * 
	 * @param sn
	 * @return
	 */
	public Long findTwiceBackTimesBySn(String sn) {
		DetachedCriteria dc = DetachedCriteria.forClass(Sn.class);
		dc.add(Restrictions.eq("sn", sn));
		dc.setProjection(Projections.rowCount());
		return (Long) hibernateTemplate.findByCriteria(dc).listIterator().next();
	}
	
	/**
	 * 查询指定sn是否在保
	 * 
	 * @param sn
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isSnKeeping(String sn) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT p.keep_days - TIMESTAMPDIFF(DAY, t.do_time, now()) as count ");
		sql.append(" FROM t_sn t JOIN sm_product p ON t.product_id = p.product_id ");
		sql.append(" AND t.sn_id = (SELECT max(s.sn_id) FROM t_sn s WHERE s.sn = '" + sn + "') ");
	
		Query q = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString())
					.setResultTransformer(Transformers.aliasToBean(NumberDto.class));
		List<NumberDto> result = q.list();
		if (result != null && result.size() == 1 && result.get(0).getCount() != null) {
			if (result.get(0).getCount().intValue() < 0) {
				return false;
			} else {
				return true;
			}
		}
		
		return false;
	}
	
}
