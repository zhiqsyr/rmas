package com.dl.rmas.service;

import java.util.List;

import com.dl.rmas.common.enums.ProduceType;
import com.dl.rmas.dto.EmployeeReturnStatis;
import com.dl.rmas.dto.EmployeeTrackDto;
import com.dl.rmas.dto.ProduceDto;
import com.dl.rmas.entity.SnProduce;
import com.dl.rmas.web.zkmodel.PagingDto;

public interface SnProduceService extends BaseService {

	void doStart(Integer snId, Integer producer, ProduceType produceType);
	
	void doFinishRepair(Integer snId, Integer producer, ProduceType produceType, String result, String resultRemark, Integer repairCode);
	
	void doFinal(Integer snId, Integer producer, ProduceType produceType, String result, String resultRemark);
	
	List<SnProduce> querySnProducesBySnId(Integer snId);
	
	/**
	 * 查询员工生产效益
	 * 
	 * @param query
	 * @param pagingDto
	 * @return
	 */
	List<EmployeeTrackDto> queryTrackByQueryDto(SnProduce query, PagingDto pagingDto);
	
	/**
	 * <b>Function: <b>查询员工返修情况
	 *
	 * @param query
	 * @return
	 */
	List<EmployeeReturnStatis> queryEmployeeReturnStatis(EmployeeReturnStatis query);
	
	/**
	 * <b>Function: <b>导出员工绩效
	 *
	 * @param query
	 * @throws Exception
	 */
	void doExport(SnProduce query) throws Exception;
	
	/**
	 * <b>Function: <b>导出员工返修情况
	 *
	 * @param statis
	 * @throws Exception
	 */
	void doExportReturn(List<EmployeeReturnStatis> statis) throws Exception;
	
	/**
	 * <b>Function: <b>导出返修明细
	 *
	 * @param query
	 * @throws Exception
	 */
	void doExportReturnDetail(EmployeeReturnStatis query) throws Exception;
	
	/**
	 * <b>Function: <b>导出员工绩效明细 
	 *
	 * @param query
	 * @throws Exception
	 */
	void doExportDetail(SnProduce query) throws Exception;
	
	/**
	 * <b>Function: <b>导出 QC 绩效数据时，设置维修工程师信息
	 *
	 * @param snId
	 * @param produceId
	 */
	void doSetRepairs4ProduceDto(ProduceDto produceDto, Integer snId, Integer produceId);
	
}
