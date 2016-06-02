package com.dl.rmas.service.impl;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.zkoss.zul.Filedownload;

import com.dl.rmas.common.enums.FinalResult;
import com.dl.rmas.common.enums.IF;
import com.dl.rmas.common.enums.ProduceType;
import com.dl.rmas.common.enums.RepairResult;
import com.dl.rmas.common.enums.SnStatus;
import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.common.utils.DateUtils;
import com.dl.rmas.common.utils.PrintLabel;
import com.dl.rmas.dao.SnDao;
import com.dl.rmas.dto.ProduceDto;
import com.dl.rmas.dto.RepairingDto;
import com.dl.rmas.dto.SnDto;
import com.dl.rmas.entity.DictCode;
import com.dl.rmas.entity.Order;
import com.dl.rmas.entity.OrderDo;
import com.dl.rmas.entity.Product;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.service.OrderDoService;
import com.dl.rmas.service.OrderService;
import com.dl.rmas.service.SnProduceService;
import com.dl.rmas.service.SnService;
import com.dl.rmas.web.zkmodel.PagingDto;

@Service("snService")
public class SnServiceImpl extends BaseServiceImpl implements SnService {

	@Autowired
	private SnDao snDao;
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private SnProduceService snProduceService;
	@Autowired
	private OrderDoService orderDoService;
	
	@Override
	public void doCreateSn(Sn sn, Order order) {
		sn.setSnIndex(order.getTotalFinished() + 1);
//		sn.setStatus(SnStatus.WAIT_L1KEYIN);
		sn.setHardLevel(0);
		
		sn.setCreateUser(currentUserId());
		sn.setCreateTime(currentTime());
		sn.setValidity(Validity.VALID);
		
		doSave(sn);
		
		order.setTotalFinished(order.getTotalFinished() + 1);
		order.setTotalRemain(order.getTotalRemain() - 1);
//		if (order.getTotalRemain() == 0) {
//			order.setKeyinStatus(KeyinStatus.CLOSE);
//		}
		orderService.doModifyOrder(order);
		
		// 记录KEYIN操作
		snProduceService.doFinal(sn.getSnId(), currentUserId(), ProduceType.KEYIN, null, null);
	}
	
	@Override
	public void doModifySn(Sn sn) {
		sn.setLastModifier(currentUserId());
		sn.setLastModifyTime(currentTime());
		
		doUpdate(sn);
	}
	
	@Override
	public List<Sn> querySnsBySnDto(SnDto snDto, PagingDto pagingDto) {
		return snDao.findDtosByQueryDto(snDto, pagingDto);
	}
	
	@Override
	public List<Sn> queryWaitDoSnsByRma(String rma) {
		Order order = orderService.queryOrderByRma(rma);
		if (order == null) {
			return new ArrayList<Sn>();
		}
		
		SnDto snDto = new SnDto();
		snDto.setOrderId(order.getOrderId());
		snDto.setStatus(SnStatus.WAIT_DO);
		return snDao.findDtosByQueryDto(snDto, null);
	}
	
	@Override
	public List<Sn> queryAllSnsByRma(Integer orderId) {
		SnDto snDto = new SnDto();
		snDto.setOrderId(orderId);
		
		return snDao.findDtosByQueryDto(snDto, null);
	}
	
	@Override
	public List<RepairingDto> queryUserRepairingCount() {
		return snDao.findUserRepairingCount();
	}
	
	@Override
	public Long queryTwiceBackTimesBySn(String sn) {
		if (StringUtils.isBlank(sn)) {
			return 0L;
		}
		
		return snDao.findTwiceBackTimesBySn(sn);
	}
	

	@Override
	public String queryFinalResultBySn(String sn) {
		if (StringUtils.isBlank(sn)) {
			return null;
		}
		
		return snDao.findFinalresult(sn);
	}
	
	@Override
	public Timestamp queryDODateBySn(String sn) {
		if (StringUtils.isBlank(sn)) {
			return null;
		}
		
		return snDao.findDODate(sn);
	}

	@Override
	public String queryStopReasonBySn(String sn) {
		if (StringUtils.isBlank(sn)) {
			return null;
		}
		
		return snDao.findStopReason(sn);
	}

	
	
	
	
	@Override
	public boolean querySnIsKeeping(String sn) {
		return snDao.isSnKeeping(sn);
	}
	
	@Override
	public boolean isOrderSnExisting(Integer orderId, String sn) {
		Sn example = new Sn();
		example.setOrderId(orderId);
		example.setSn(sn);
		return queryByExample(example).size() > 0 ? true : false;
	}
	
	@Override
	public boolean isSnExisting(String sn) {
		Sn example = new Sn();
		example.setSn(sn);
		return queryByExample(example).size() > 0 ? true : false;
	}
	
	@Override
	public void doFlashOk(List<Sn> sns) {
		for (Sn sn : sns) {
			sn.setStatus(SnStatus.WAIT_QC);
			sn.setFlasher(currentUserId());
			sn.setFlashTime(currentTime());
			sn.setFlashResult("OK");
			
			doModifySn(sn);
			snProduceService.doFinal(sn.getSnId(), currentUserId(), ProduceType.FLASH, sn.getStatus().name(), null);
		}
	}
	
	@Override
	public void doFlashReturn(List<Sn> sns) {
		for (Sn sn : sns) {
			sn.setStatus(SnStatus.WAIT_L1KEYIN);
			sn.setFlasher(currentUserId());
			sn.setFlashTime(currentTime());
			sn.setFlashResult("NG");
			
			doModifySn(sn);
			snProduceService.doFinal(sn.getSnId(), currentUserId(), ProduceType.FLASH_NG, sn.getStatus().name(), null);
		}
	}
	
	@Override
	public void doAssign(List<Sn> sns, Integer userId, Boolean isMidh) {
//		Product product;
//		DictCode dictCode;
		for (Sn sn : sns) {
			if (sn.getHardLevel() == 0) {
				/** 手机等移动设备，首先尝试软件维修WAIT_FLASH */
//				product = productService.queryById(Product.class, sn.getProductId());
//				dictCode = dictTypeCodeService.queryById(DictCode.class, product.getProductType());
//				if (IF.YES.name().equals(dictCode.getCodeDesc())) {
				
				if (Boolean.TRUE.equals(isMidh)) {
					sn.setStatus(SnStatus.WAIT_FLASH);
				} else {
					sn.setStatus(SnStatus.WAIT_REPAIRING);
				}
			} else {
				sn.setStatus(SnStatus.WAIT_REPAIRING);
			}
			
			sn.setHardLevel(sn.getHardLevel() + 1);
			sn.setRepairer(userId);
			sn.setAssigner(currentUserId());
			sn.setAssignTime(currentTime());
			
			doModifySn(sn);
			
			if (Boolean.TRUE.equals(isMidh)) {
				snProduceService.doFinal(sn.getSnId(), currentUserId(), ProduceType.MIDH, sn.getStatus().name(), null);
			} else {
				snProduceService.doFinal(sn.getSnId(), currentUserId(), ProduceType.L1KEYIN, sn.getStatus().name(), null);
			}
			
		}
	}
	
	@Override
	public void doFinal(List<Sn> sns, FinalResult finalResult, String stopReason) {
		for (Sn sn : sns) {
			sn.setFinalResult(finalResult);
			sn.setStopReason(stopReason);
			sn.setStatus(SnStatus.WAIT_DO);
			sn.setStoper(currentUserId());
			sn.setStopTime(currentTime());
			
			
			doModifySn(sn);
			
			snProduceService.doFinal(sn.getSnId(), currentUserId(), 
					ProduceType.STOP_REPAIR, finalResult.name(), stopReason);
		}
	}
	
	@Override
	public void doRepair(List<Sn> sns, RepairResult repairResult, Integer repairCode, IF materialUsed, String repairRemark) {
		ProduceType type = null;
		for (Sn sn : sns) {
			if (RepairResult.REPAIR_OK.equals(repairResult)) {
				sn.setStatus(SnStatus.WAIT_QC);
				sn.setRepairResult("OK");
				
				type = ProduceType.REPAIR;
			} else if (RepairResult.REPAIR_RETURN.equals(repairResult)) {
				sn.setStatus(SnStatus.WAIT_L1KEYIN);
				sn.setRepairResult("NG");
				
				type = ProduceType.REPAIR_NG;
			}
			
			sn.setMaterialUsed(materialUsed);
			sn.setRepairCode(repairCode);
			sn.setRepairer(currentUserId());
			sn.setRepairRemark(repairRemark);
			sn.setRepairedTime(currentTime());
			
			doModifySn(sn);
			
			snProduceService.doFinishRepair(sn.getSnId(), currentUserId(), type, sn.getStatus().name(), repairRemark, repairCode);
		}
	}
	
	@Override
	public void doQc(List<Sn> sns, FinalResult finalResult, IF materialUsed,
			String macImei1N, String qcRemark) {
		ProduceType type = null;
		for (Sn sn : sns) {
			if (FinalResult.OK.equals(finalResult)) {
				sn.setStatus(SnStatus.WAIT_DO);
				sn.setQcResult("OK");
				sn.setFinalResult(finalResult);
				
				type = ProduceType.QC;
			} else if (FinalResult.NG.equals(finalResult)) {
				sn.setStatus(SnStatus.WAIT_L1KEYIN);
				sn.setQcResult("NG");
				
				type = ProduceType.QC_NG;
			}
			
			sn.setMacImei1N(macImei1N);
			sn.setMaterialUsed(materialUsed);
			sn.setQcer(currentUserId());
			sn.setQcRemark(qcRemark);
			sn.setQcTime(currentTime());
			
			doModifySn(sn);
			
			snProduceService.doFinal(sn.getSnId(), currentUserId(), type, finalResult.name(), qcRemark);
		}
	}
	
	@Override
	public void doOQc(List<Sn> sns, FinalResult finalResult, String oqcRemark) {
		ProduceType type = null;
		for (Sn sn : sns) {
			if (FinalResult.OK.equals(finalResult)) {
				sn.setFinalResult(finalResult);
				sn.setOqcResult("OK");
				
				type = ProduceType.OQC_OK;
			} else if (FinalResult.NG.equals(finalResult)) {
				sn.setStatus(SnStatus.WAIT_L1KEYIN);
				sn.setOqcResult("NG");
				sn.setOqcRemark(oqcRemark);//
				
				type = ProduceType.OQC_NG;
			}
			
			doModifySn(sn);
			
			snProduceService.doFinal(sn.getSnId(), currentUserId(), type, finalResult.name(), oqcRemark);//
		}
	}
	
	@Override
	public void doKeyout(List<Sn> sns) {
		for (Sn sn : sns) {
			sn.setStatus(SnStatus.WAIT_DO);
			sn.setKeyouter(currentUserId());
			sn.setKeyoutTime(currentTime());
			
			doModifySn(sn);
			
			snProduceService.doFinal(sn.getSnId(), currentUserId(), ProduceType.KEYOUT, null, null);
		}
	}
	
	@Override
	public void doReturnQc(List<Sn> sns) {
		for (Sn sn : sns) {
			sn.setStatus(SnStatus.WAIT_QC);
			
			doModifySn(sn);
			
			snProduceService.doFinal(sn.getSnId(), currentUserId(), ProduceType.RETURN_QC, null, null);
		}
	}
	
	@Override
	public void doDo(List<Sn> sns) throws Exception {
//		Integer orderId = sns.get(0).getOrderId();
		OrderDo orderDo = orderDoService.doCreateAndExportOrderDo(null, sns);
		
		Set<Integer> orderIds = new HashSet<Integer>();
		
		for (Sn sn : sns) {
			sn.setStatus(SnStatus.DONE);
			sn.setDoId(orderDo.getDoId());
			sn.setDoer(currentUserId());
			sn.setDoTime(currentTime());
			
			doModifySn(sn);
			
			snProduceService.doFinal(sn.getSnId(), currentUserId(), ProduceType.DO, null, null);
			
			orderIds.add(sn.getOrderId());
		}
		
		// 全部DO，订单 DO Status 置为 ‘DONE’
		for (Integer id : orderIds) {
			if (snDao.findSurplusUndoCount(id) == 0) {
				orderService.doDoOrder(id);
			}			
		}
	}
	
	@Override
	public void doExportExcel(SnDto queryDto) throws Exception {
		List<ProduceDto> sns = snDao.findProduceDtosByQueryDto(queryDto);
		
		doExport(sns, "/Config/template/template_produce_query.xls");
	}
	
	@Override
	public void doExportExcelLine(SnDto queryDto) throws Exception {
		List<ProduceDto> sns = snDao.findProduceDtosByQueryDto(queryDto);
		
		List<ProduceDto> result = new ArrayList<ProduceDto>();
		ProduceDto prod = null, dto;
		for (int i = 0; i < sns.size(); i++) {
			dto = sns.get(i);
			if (i == 0 || !dto.getSn().equals(sns.get(i-1).getSn())) {
				prod = dto;
				result.add(prod);
			} else {
				prod.setPartName(prod.getPartName() + ", " + dto.getPartName());
				prod.setCate(prod.getCate() + ", " + dto.getCate());
				prod.setUsedAmount(prod.getUsedAmount() + ", " + dto.getUsedAmount());
			}
		}
		
		doExport(result, "/Config/template/template_produce_query_line.xls");
	}
	
	private void doExport(List<ProduceDto> sns, String templatePath) throws Exception {
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("sns", sns);
		
		// TODO del for deploy
		String tmp = "/Users/zhiqsyr/dev/temp";
		String path = tmp + "/data/rmas/ComprehensiveSearch/";
		
		// 1）生成EXCEL
		XLSTransformer transformer = new XLSTransformer();
		ClassPathResource cpr = new ClassPathResource(templatePath);
		Workbook wb = transformer.transformXLS(cpr.getInputStream(), beans);
		
		File fold = new File(path);
		if (!fold.isDirectory()) {
			fold.mkdirs();
		}
		
		String excelFileName = "ComprehensiveSearch_" + DateUtils.transferDate2Str(new Date(), "yyyyMMddHHmmss") + ".xls";
		OutputStream os = new FileOutputStream(new File(path + excelFileName));
		wb.write(os);
		os.flush();
		os.close();
		
		Filedownload.save(new File(path + excelFileName), "application/x-download");		
	}
	
	@Override
	public void doPrint(Integer snId) throws Exception {
		Map<String, String> args = new HashMap<String, String>();
		
		Sn sn = queryById(Sn.class, snId);
		args.put("rma.no", sn.getOrder().getRma());
		args.put("item.no", sn.getSnIndexFormatted());
		args.put("sn", sn.getSn());
		Product product = queryById(Product.class, sn.getProductId());
		args.put("part.cust", product.getPn());
		args.put("warr", sn.getKeepStatus().name());
		
		args.put("brand", StringUtils.isBlank(product.getBrand()) ? "" : product.getBrand());
		args.put("r.t", sn.getTwiceBackTimes().toString());
		args.put("type", StringUtils.isBlank(product.getModelNo()) ? "" : product.getModelNo());
		args.put("desc", StringUtils.isBlank(sn.getRemark()) ? "" : sn.getRemark());
		args.put("mb.model", StringUtils.isBlank(product.getPcbType()) ? "" : product.getPcbType());
		if (sn.getCustomerFaultDesc() != null) {
			DictCode customerFaultDesc = queryById(DictCode.class, sn.getCustomerFaultDesc());
			args.put("customer.fault.desc", customerFaultDesc == null ? "" : customerFaultDesc.getCodeName());
		} else {
			args.put("customer.fault.desc", "");
		}
		
		
        // 通俗理解就是书、文档  
        Book book = new Book();  
        // 打印格式  
        PageFormat pf = new PageFormat();  
        pf.setOrientation(PageFormat.PORTRAIT);  
  
        // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。  
        Paper p = new Paper();  
        p.setSize(175, 118); 
        p.setImageableArea(0, 0, 175, 118);  
        pf.setPaper(p);  
        // 把 PageFormat 和 Printable 添加到书中，组成一个页面  
        PrintLabel pl = new PrintLabel();
        pl.setArgs(args);
        book.append(pl, pf);  
  
        // 获取打印服务对象  
        PrinterJob job = PrinterJob.getPrinterJob();  
        job.setPageable(book);  
//    	if (job.printDialog()) {
//    		job.print();  
//		}
        
        try {
        	job.print();  
		} catch (Exception e) {
			LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
		}
	}



}
