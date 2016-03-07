package com.dl.rmas.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.zkoss.zul.Filedownload;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.common.enums.ProduceType;
import com.dl.rmas.common.utils.DateUtils;
import com.dl.rmas.common.utils.JxlsUtils;
import com.dl.rmas.dao.SnProductDao;
import com.dl.rmas.dto.EmployeeReturnStatis;
import com.dl.rmas.dto.EmployeeTrackDto;
import com.dl.rmas.dto.ProduceDto;
import com.dl.rmas.entity.DictCode;
import com.dl.rmas.entity.SnProduce;
import com.dl.rmas.entity.User;
import com.dl.rmas.service.SnProduceService;
import com.dl.rmas.web.zkmodel.PagingDto;

@Service("snProduceService")
public class SnProduceServiceImpl extends BaseServiceImpl implements SnProduceService {

	@Autowired
	private SnProductDao snProductDao;
	
	@Override
	public void doStart(Integer snId, Integer producer, ProduceType produceType) {
		SnProduce snProduce = new SnProduce();
		snProduce.setSnId(snId);
		snProduce.setProducer(producer);
		snProduce.setProduceType(produceType);
		snProduce.setStartTime(currentTime());
		
		doSave(snProduce);
	}
	
	@Override
	public void doFinishRepair(Integer snId, Integer producer,
			ProduceType produceType, String result, String resultRemark,
			Integer repairCode) {
		SnProduce snProduce = new SnProduce();
		snProduce.setSnId(snId);
		snProduce.setProducer(producer);
		snProduce.setProduceType(produceType);
		snProduce.setResult(result);
		snProduce.setResultRemark(resultRemark);
		snProduce.setStartTime(currentTime());
		snProduce.setEndTime(currentTime());
		snProduce.setRepairCode(repairCode);
		
		doSave(snProduce);
	}
	
	@Override
	public void doFinal(Integer snId, Integer producer,
			ProduceType produceType, String result, String resultRemark) {
		SnProduce snProduce = new SnProduce();
		snProduce.setSnId(snId);
		snProduce.setProducer(producer);
		snProduce.setProduceType(produceType);
		snProduce.setResult(result);
		snProduce.setResultRemark(resultRemark);
		snProduce.setStartTime(currentTime());
		snProduce.setEndTime(currentTime());
		
		doSave(snProduce);
	}
	
	@Override
	public List<SnProduce> querySnProducesBySnId(Integer snId) {
		return snProductDao.findSnProducesBySnId(snId);
	}
	
	@Override
	public List<EmployeeTrackDto> queryTrackByQueryDto(SnProduce query,
			PagingDto pagingDto) {
		return snProductDao.findTrackByQueryDto(query, pagingDto);
	}
	
	@Override
	public List<EmployeeReturnStatis> queryEmployeeReturnStatis(
			EmployeeReturnStatis query) {
		return snProductDao.findEmployeeReturnStatis(query);
	}
	
	@Override
	public void doExport(SnProduce query) throws Exception {
		List<EmployeeTrackDto> result = queryTrackByQueryDto(query, null);
		
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("result", result);
		String path = "/data/rmas/ReportForms/";
		
		// 1）生成EXCEL
		XLSTransformer transformer = new XLSTransformer();
		ClassPathResource cpr = new ClassPathResource("/Config/template/template_employee_benefit.xls");
		Workbook wb = transformer.transformXLS(cpr.getInputStream(), beans);		
		
		File fold = new File(path);
		if (!fold.isDirectory()) {
			fold.mkdirs();
		}
		
		String excelFileName = "Employee Benefit_" + DateUtils.transferDate2Str(new Date(), "yyyyMMddHHmmss") + ".xls";
		OutputStream os = new FileOutputStream(new File(path + excelFileName));
		wb.write(os);
		os.flush();
		os.close();
		
		Filedownload.save(new File(path + excelFileName), "application/x-download");
	}
	
	@Override
	public void doExportReturn(List<EmployeeReturnStatis> statis)
			throws Exception {
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("result", statis);
		
		String excelRealPath = JxlsUtils.exportExcel(beans, Constants.FOLDER_REPORT_FORMS, 
				"/Config/template/template_employee_return.xls", 
				"Employee Return_" + DateUtils.transferDate2Str(new Date(), "yyyyMMddHHmmss") + ".xls");
		
		Filedownload.save(new File(excelRealPath), "application/x-download");
	}
	
	@Override
	public void doExportReturnDetail(EmployeeReturnStatis query)
			throws Exception {
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("sns", snProductDao.findReturnStatisDetails(query));
		
		String excelRealPath = JxlsUtils.exportExcel(beans, Constants.FOLDER_REPORT_FORMS, 
				"/Config/template/template_employee_return_detail.xls", 
				"Employee Return Detail_" + DateUtils.transferDate2Str(new Date(), "yyyyMMddHHmmss") + ".xls");
		
		Filedownload.save(new File(excelRealPath), "application/x-download");
		
	}
	
	@Override
	public void doExportDetail(SnProduce query) throws Exception {
		List<ProduceDto> result;
		String template;
		
		// QC 时，导出模板不同
		if (query.getProduceType().equals(ProduceType.QC)
				|| query.getProduceType().equals(ProduceType.QC_NG)) {
			result = snProductDao.findProduceDtosByQuery4QC(query);
			for (ProduceDto dto : result) {
				doSetRepairs4ProduceDto(dto, dto.getSnId(), dto.getProduceId());
			}
			
			template = "/Config/template/template_employee_benefit_detail_qc.xls";
		} else {
			result = snProductDao.findProduceDtosByQuery(query);
			template = "/Config/template/template_employee_benefit_detail.xls";
		}
		
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("result", result);
		String path = "/data/rmas/ReportForms/";
		
		// 1）生成EXCEL
		XLSTransformer transformer = new XLSTransformer();
		ClassPathResource cpr = new ClassPathResource(template);
		Workbook wb = transformer.transformXLS(cpr.getInputStream(), beans);		
		
		File fold = new File(path);
		if (!fold.isDirectory()) {
			fold.mkdirs();
		}
		
		String excelFileName = "Employee Benefit Detail_" + DateUtils.transferDate2Str(new Date(), "yyyyMMddHHmmss") + ".xls";
		OutputStream os = new FileOutputStream(new File(path + excelFileName));
		wb.write(os);
		os.close();
		
		Filedownload.save(new File(path + excelFileName), "application/x-download");		
	}
	
	@Override
	public void doSetRepairs4ProduceDto(ProduceDto produceDto, Integer snId, Integer produceId) {
		SnProduce repair = snProductDao.findRepairInformation(snId, produceId);
		if (repair == null) {
			return;
		}
		
		User u = queryById(User.class, repair.getProducer());
		produceDto.setRepairerNo(u.getUserNo());
		produceDto.setRepairerName(u.getUserName());
		if (repair.getRepairCode() != null) {
			DictCode dc = queryById(DictCode.class, repair.getRepairCode());
			produceDto.setRepairCode(dc != null ? dc.getCodeName() : null);
		}
		produceDto.setRepairRemark(repair.getResultRemark());
		produceDto.setRepairResult("WAIT_QC".equals(repair.getResult()) ? "OK" : "NG");
	}
	
}
