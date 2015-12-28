package com.dl.rmas.service;

import java.util.List;

import com.dl.rmas.dto.OrderTrackDto;
import com.dl.rmas.entity.Order;
import com.dl.rmas.web.zkmodel.PagingDto;

public interface OrderService extends BaseService {

	Order queryOrderByRma(String rma);
	
	/**
	 * 查询指定costomerId最大RMA编号
	 *
	 * @param customerId
	 * @return
	 */
	String queryMaxRmaByCustomerId(Integer customerId);

	List<Order> queryOrdersByQueryDto(Order queryOrder, PagingDto pagingDto);
	
	/**
	 * 订单跟踪结果列表
	 * 
	 * @param queryOrder
	 * @param pagingDto
	 * @return
	 */
	List<OrderTrackDto> queryTrackByQueryDto(Order queryOrder, PagingDto pagingDto);
	
	void doCreateOrder(Order order);
	
	void doModifyOrder(Order order);

	void doFinishKeyin(Order order) throws Exception;
	
	void doExportTrack(Order query) throws Exception;
	
	/**
	 * <b>Function: <b>orderId doStatus 置为 ‘DONE'
	 *
	 * @param orderId
	 */
	void doDoOrder(Integer orderId);
	
	/**
	 * <b>Function: <b>导出 Receive 记录到 Excel
	 *
	 * @param query
	 * @throws Exception
	 */
	void doExportReceive(Order query) throws Exception;
	
}
