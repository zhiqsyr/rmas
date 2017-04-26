package com.dl.rmas.service;

import java.util.List;

import com.dl.rmas.entity.OrderDo;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.web.zkmodel.PagingDto;

public interface OrderDoService extends BaseService {

	/**
	 * 生成Excel与Word格式出货单，创建出货记录
	 *
	 * @param orderId
	 * @param selecteds	被选中的维修任务，可能是多个
	 * @return
	 */
	OrderDo doCreateAndExportOrderDo(Integer orderId, List<Sn> selecteds) throws Exception;
	
	/**
	 * 查询指定costomerId最大RMA编号
	 *
	 * @param customerId
	 * @return
	 */
	String queryMaxRmaByCustomerId(Integer customerId);
	
	List<OrderDo> queryOrderDosByQueryDto(OrderDo query, PagingDto pagingDto);
	
}
