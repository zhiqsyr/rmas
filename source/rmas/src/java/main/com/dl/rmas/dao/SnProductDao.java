package com.dl.rmas.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dl.rmas.common.enums.ProduceType;
import com.dl.rmas.dto.EmployeeTrackDto;
import com.dl.rmas.dto.ProduceDto;
import com.dl.rmas.entity.SnProduce;
import com.dl.rmas.web.zkmodel.PagingDto;

@Repository
public class SnProductDao extends BaseDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unchecked")
	public List<SnProduce> findSnProducesBySnId(Integer snId) {
		List<SnProduce> result = (List<SnProduce>) hibernateTemplate.find(
				"from SnProduce where snId = ? order by produceId", snId);
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<EmployeeTrackDto> findTrackByQueryDto(SnProduce query, PagingDto pagingDto) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT u.user_id userId, u.user_no userNo, u.user_name userName, ");
		sql.append(" 	sum(CASE p.produce_type WHEN 'KEYIN' THEN 1 ELSE 0 END) keyinCount, ");
		sql.append(" 	sum(CASE p.produce_type WHEN 'MIDH' THEN 1 ELSE 0 END) midhCount, ");
		sql.append(" 	sum(CASE p.produce_type WHEN 'FLASH' THEN 1 ELSE 0 END) flashCount, ");
		sql.append(" 	sum(CASE p.produce_type WHEN 'FLASH_NG' THEN 1 ELSE 0 END) flashNgCount, ");
		sql.append(" 	sum(CASE p.produce_type WHEN 'L1KEYIN' THEN 1 ELSE 0 END) l1keyinCount, ");
		sql.append(" 	sum(CASE p.produce_type WHEN 'STOP_REPAIR' THEN 1 ELSE 0 END) stopRepairCount, ");
		sql.append(" 	sum(CASE p.produce_type WHEN 'REPAIR' THEN 1 ELSE 0 END) repairCount, ");
		sql.append(" 	sum(CASE p.produce_type WHEN 'REPAIR_NG' THEN 1 ELSE 0 END) repairNgCount, ");
		sql.append(" 	sum(CASE p.produce_type WHEN 'QC' THEN 1 ELSE 0 END) qcCount, ");
		sql.append(" 	sum(CASE p.produce_type WHEN 'QC_NG' THEN 1 ELSE 0 END) qcNgCount, ");
		sql.append(" 	sum(CASE p.produce_type WHEN 'OQC_OK' THEN 1 ELSE 0 END) oqcCount, ");
		sql.append(" 	sum(CASE p.produce_type WHEN 'OQC_NG' THEN 1 ELSE 0 END) oqcNgCount, ");
		sql.append(" 	sum(CASE p.produce_type WHEN 'DO' THEN 1 ELSE 0 END) doCount ");
		sql.append(" FROM t_sn_produce p RIGHT JOIN sm_user u ON p.producer = u.user_id ");
		sql.append(" WHERE 1 = 1 ");
		if (StringUtils.isNotBlank(query.getEmployeeNo())) {
			sql.append(" and u.user_no like '%" + query.getEmployeeNo() + "%' ");
		}
		if (StringUtils.isNotBlank(query.getEmployeeName())) {
			sql.append(" and u.user_name like '%" + query.getEmployeeName() + "%' ");
		}
		if (query.getEndTimeFrom() != null) {
			sql.append(" and p.end_time >= :from ");
		}
		if (query.getEndTimeTo() != null) {
			sql.append(" and p.end_time <= :to ");
		}
		sql.append(" GROUP BY u.user_id, u.user_no, u.user_name ");
		sql.append(" ORDER BY u.user_no ");

		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		
		if (pagingDto == null) {
			SQLQuery q = session.createSQLQuery(sql.toString());
			if (query.getEndTimeFrom() != null) {
				q.setParameter("from", query.getEndTimeFrom());
			}
			if (query.getEndTimeTo() != null) {
				q.setParameter("to", DateUtils.addDays(query.getEndTimeTo(), 1));
			}
			q.setResultTransformer(Transformers.aliasToBean(EmployeeTrackDto.class));
			return q.list();			
		}		
		
		// 查询总数
		String countSql = "select count(DISTINCT u.user_id) " + sql.substring(sql.indexOf("FROM"), sql.indexOf("GROUP BY"));
		SQLQuery count = session.createSQLQuery(countSql);
		if (query.getEndTimeFrom() != null) {
			count.setParameter("from", query.getEndTimeFrom());
		}
		if (query.getEndTimeTo() != null) {
			count.setParameter("to", DateUtils.addDays(query.getEndTimeTo(), 1));
		}
		pagingDto.setTotalSize(Long.parseLong(count.list().get(0).toString()));
		
		// 查询当前分页数据
		SQLQuery q = session.createSQLQuery(sql.toString());
		if (query.getEndTimeFrom() != null) {
			q.setParameter("from", query.getEndTimeFrom());
		}
		if (query.getEndTimeTo() != null) {
			q.setParameter("to", DateUtils.addDays(query.getEndTimeTo(), 1));
		}
		q.setResultTransformer(Transformers.aliasToBean(EmployeeTrackDto.class));
		q.setFirstResult(pagingDto.getActivePage() * pagingDto.getPageSize());
		q.setMaxResults(pagingDto.getPageSize());
		return q.list();
	}
	
	public List<ProduceDto> findProduceDtosByQuery(SnProduce query) {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		
		sql.append("SELECT u.user_no employeeNo, u.user_name employeeName, sp.produce_type produceType, ");
		sql.append("	o.custrma, o.rma, s.sn_index snIndex, s.sn, p.pn, p.pcb_type pcbType, dc.code_key productType, ");
		sql.append("	o.keyin_status keyinStatus, s.hard_level hardLevel, date_format(o.receive_time, '%Y-%m-%d') receiveTime,");
		sql.append("	date_format(s.create_time, '%Y-%m-%d') createTime, date_format(s.assign_time, '%Y-%m-%d') assignTime,");
		sql.append("	date_format(s.repaired_time, '%Y-%m-%d') repairedTime, date_format(s.qc_time, '%Y-%m-%d') qcTime,");
		sql.append("	date_format(s.do_time, '%Y-%m-%d') doTime, date_format(o.close_time, '%Y-%m-%d') closeTime,");
		sql.append("	s.status, flasher.user_name flasherName, flasher.user_no flasherNo, s.flash_result flashResult,");
		sql.append("	repairer.user_name repairerName, repairer.user_no repairerNo, code.code_name repairCode,");
		sql.append("	s.repair_remark repairRemark, s.repair_result repairResult, s.material_used materialUsed,");
		sql.append("	qcer.user_name qcerName, qcer.user_no qcerNo,");
		sql.append("	s.qc_result qcResult, stoper.user_name stoperName, stoper.user_no stoperNo, s.stop_reason stopReason,");
		sql.append("	s.final_result finalResult, s.keep_status keepStatus, s.mac_imei1 macImei1, s.mac_imei1_n macImei1N,");
		sql.append("	s.mac_imei2 macImei2, s.mac_imei2_n macImei2N ");
		sql.append("FROM (SELECT t.* FROM t_sn_produce t WHERE 1 = 1 and t.produce_type = ? ");
		params.add(query.getProduceType().name());
		if (query.getEndTimeFrom() != null) {
			sql.append(" and t.end_time >= ? ");
			params.add(query.getEndTimeFrom());
		}
		if (query.getEndTimeTo() != null) {
			sql.append(" and t.end_time <= ? ");
			params.add(DateUtils.addDays(query.getEndTimeTo(), 1));
		}			
		sql.append(" 	 ) sp ");
		sql.append("JOIN sm_user u ON sp.producer = u.user_id ");
		sql.append("JOIN t_sn s ON sp.sn_id = s.sn_id ");
		sql.append("JOIN t_order o ON s.order_id = o.order_id ");
		sql.append("JOIN sm_product p ON s.product_id = p.product_id ");
		sql.append("LEFT JOIN sm_dict_code dc ON p.product_type = dc.code_id ");
		sql.append("LEFT JOIN sm_user flasher ON s.flasher = flasher.user_id ");
		sql.append("LEFT JOIN sm_user repairer ON s.repairer = repairer.user_id ");
		sql.append("LEFT JOIN sm_user qcer ON s.qcer = qcer.user_id ");
		sql.append("LEFT JOIN sm_user stoper ON s.stoper = stoper.user_id ");
		sql.append("LEFT JOIN sm_dict_code code ON s.repair_code = code.code_id ");
		sql.append("WHERE 1 = 1 ");
		if (StringUtils.isNotBlank(query.getEmployeeNo())) {
			sql.append(" and u.user_no like '%" + query.getEmployeeNo() + "%' ");
		}
		if (StringUtils.isNotBlank(query.getEmployeeName())) {
			sql.append(" and u.user_name like '%" + query.getEmployeeName() + "%' ");
		}
		sql.append("ORDER BY u.user_no, o.rma, s.sn_index ");

		return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper(ProduceDto.class));
	}
	
	public List<ProduceDto> findProduceDtosByQuery4QC(SnProduce query) {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		
		sql.append("SELECT sp.produce_id produceId, s.sn_id snId, u.user_no employeeNo, u.user_name employeeName, sp.produce_type produceType, ");
		sql.append("	o.custrma, o.rma, s.sn_index snIndex, s.sn, p.pn, p.pcb_type pcbType, dc.code_key productType, ");
		sql.append("	o.keyin_status keyinStatus, s.hard_level hardLevel, date_format(o.receive_time, '%Y-%m-%d') receiveTime,");
		sql.append("	date_format(s.create_time, '%Y-%m-%d') createTime, date_format(s.assign_time, '%Y-%m-%d') assignTime,");
		sql.append("	date_format(s.repaired_time, '%Y-%m-%d') repairedTime, date_format(s.qc_time, '%Y-%m-%d') qcTime,");
		sql.append("	date_format(s.do_time, '%Y-%m-%d') doTime, date_format(o.close_time, '%Y-%m-%d') closeTime,");
		sql.append("	s.status, flasher.user_name flasherName, flasher.user_no flasherNo, s.flash_result flashResult,");
		sql.append("	s.material_used materialUsed, ");
		sql.append("	u.user_name qcerName, u.user_no qcerNo,	sp.result qcResult, sp.result_remark qcRemark,");
		sql.append("	s.final_result finalResult, s.keep_status keepStatus, s.mac_imei1 macImei1, s.mac_imei1_n macImei1N,");
		sql.append("	s.mac_imei2 macImei2, s.mac_imei2_n macImei2N ");
		sql.append("FROM (SELECT t.* FROM t_sn_produce t WHERE 1 = 1 ");
		if (ProduceType.QC.equals(query.getProduceType())) {
			sql.append(" and t.produce_type = 'QC' and t.result = 'OK' ");
		} else if (ProduceType.QC_NG.equals(query.getProduceType())) {
			sql.append(" and t.produce_type = 'QC_NG' ");
		}
		if (query.getEndTimeFrom() != null) {
			sql.append(" and t.end_time >= ? ");
			params.add(query.getEndTimeFrom());
		}
		if (query.getEndTimeTo() != null) {
			sql.append(" and t.end_time <= ? ");
			params.add(DateUtils.addDays(query.getEndTimeTo(), 1));
		}			
		sql.append(" 	 ) sp ");
		sql.append("JOIN sm_user u ON sp.producer = u.user_id ");
		sql.append("JOIN t_sn s ON sp.sn_id = s.sn_id ");
		sql.append("JOIN t_order o ON s.order_id = o.order_id ");
		sql.append("JOIN sm_product p ON s.product_id = p.product_id ");
		sql.append("LEFT JOIN sm_dict_code dc ON p.product_type = dc.code_id ");
		sql.append("LEFT JOIN sm_user flasher ON s.flasher = flasher.user_id ");
		sql.append("LEFT JOIN sm_dict_code code ON s.repair_code = code.code_id ");
		sql.append("WHERE 1 = 1 ");
		if (StringUtils.isNotBlank(query.getEmployeeNo())) {
			sql.append(" and u.user_no like '%" + query.getEmployeeNo() + "%' ");
		}
		if (StringUtils.isNotBlank(query.getEmployeeName())) {
			sql.append(" and u.user_name like '%" + query.getEmployeeName() + "%' ");
		}
		sql.append("ORDER BY u.user_no, o.rma, s.sn_index ");

		return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper(ProduceDto.class));
	}	
	
	/**
	 * <b>Function: <b>获得最近一次维修信息
	 *
	 * @param snId
	 * @param produceId
	 * @return
	 */
	public SnProduce findRepairInformation(Integer snId, Integer produceId) {
		DetachedCriteria dc = DetachedCriteria.forClass(SnProduce.class);
		
		dc.add(Restrictions.eq("snId", snId));
		dc.add(Restrictions.lt("produceId", produceId));
		dc.add(Restrictions.eq("produceType", ProduceType.REPAIR));
		dc.addOrder(org.hibernate.criterion.Order.desc("produceId"));
		
		List<SnProduce> result = (List<SnProduce>) hibernateTemplate.findByCriteria(dc, 0, 1);
		return result.size() == 0 ? null : result.get(0);
	}
	
}
