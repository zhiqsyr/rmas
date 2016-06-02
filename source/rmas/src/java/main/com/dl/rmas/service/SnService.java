package com.dl.rmas.service;

import java.sql.Timestamp;
import java.util.List;

import com.dl.rmas.common.enums.FinalResult;
import com.dl.rmas.common.enums.IF;
import com.dl.rmas.common.enums.RepairResult;
import com.dl.rmas.dto.RepairingDto;
import com.dl.rmas.dto.SnDto;
import com.dl.rmas.entity.Order;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.web.zkmodel.PagingDto;

public interface SnService extends BaseService {

	void doCreateSn(Sn sn, Order order);
	
	void doModifySn(Sn sn);
	
	List<Sn> querySnsBySnDto(SnDto snDto, PagingDto pagingDto);
	
	List<Sn> queryWaitDoSnsByRma(String rma);
	
	List<Sn> queryAllSnsByRma(Integer orderId);
	
	/**
	 * 员工在修产品数量
	 * 
	 * @return
	 */
	List<RepairingDto> queryUserRepairingCount();
	
	/**
	 * 查询指定sn的二反次数
	 * 
	 * @param sn
	 * @return
	 */
	Long queryTwiceBackTimesBySn(String sn);
	/**
	 * 查询指定sn的doresult状态
	 */
	String queryFinalResultBySn(String sn);
	
	/**
	 * 查询指定sn的DODate(出货日期)
	 */
	Timestamp queryDODateBySn(String sn);
	/**
	 * 查询指定sn的StopReason
	 */
	String queryStopReasonBySn(String sn);
	/**
	 * 查询指定sn是否在保
	 * 
	 * @param sn
	 * @return
	 */
	boolean querySnIsKeeping(String sn);
	/**
	 * sn是否已经存在
	 *
	 * @param sn
	 * @return
	 */
	boolean isOrderSnExisting(Integer orderId, String sn);
	boolean isSnExisting(String sn);
	
	void doFlashOk(List<Sn> sns);
	void doFlashReturn(List<Sn> sns);
	
	void doAssign(List<Sn> sns, Integer userId, Boolean isMidh);
	void doFinal(List<Sn> sns, FinalResult finalResult, String stopReason);
	
	void doRepair(List<Sn> sns, RepairResult repairResult, Integer repairCode, IF materialUsed, String repairRemark);
	
	void doQc(List<Sn> sns, FinalResult finalResult, IF materialUsed, String macImei1N, String qcRemark);

	void doOQc(List<Sn> sns, FinalResult finalResult, String oqcRemark);
	
	void doKeyout(List<Sn> sns);
	
	void doReturnQc(List<Sn> sns);
	
	void doDo(List<Sn> sns) throws Exception;
	
	/**
	 * 查询满足 queryDto 到 Excel
	 *
	 * @param queryDto
	 */
	void doExportExcel(SnDto queryDto) throws Exception;
	/**
	 * 查询满足 queryDto 到 Excel , 用料情况列在一行
	 * 
	 * @param queryDto
	 * @throws Exception
	 */
	void doExportExcelLine(SnDto queryDto) throws Exception;
	
	/**
	 * <b>Function: <b>打印 SN 标签
	 *
	 * @param snId
	 */
	void doPrint(Integer snId) throws Exception;
	
}
