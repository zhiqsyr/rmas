package com.dl.core.jxls.export;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import cn.sh.cares.template.velocity.VelocityTemplateEngine;

import com.dl.core.jxls.export.model.ListDataContext;
import com.dl.core.jxls.export.model.SheetTemplate;
import com.dl.core.jxls.parse.ExcelTemplateParser.ParseMode;
import com.dl.rmas.entity.Customer;
import com.dl.rmas.entity.Order;
import com.dl.rmas.entity.OrderDo;
import com.dl.rmas.entity.Sn;

@ContextConfiguration("/Config/spring/applicationContext_common.xml")
public class ExportTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

//	@Test
	public void testExport() throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//				jdbcTemplate
//				.queryForList("select top 10 * from STAT_COOP_REPORT a,STAT_COOP_REPORT_1_BASE b,STAT_COOP_REPORT_2_ORG c "
//						+ " where a.report_id =b.report_id and a.report_id=c.report_id and district_code='310114000000'");

		DefaultExportProcessor processor = new DefaultExportProcessor();

		ListDataContext<Map<String, Object>> dataContext = new ListDataContext<Map<String, Object>>(
				list);
		
		dataContext.putData("Util", new Util());
//		dataContext.putData(DefaultExportProcessor.OUT_STREAM_STORE_KEY,
//				new FileOutputStream("d:/output_col.xls"));
		dataContext.putData(DefaultExportProcessor.OUT_STREAM_STORE_KEY,
				new ByteArrayOutputStream(512));
		
		SheetTemplate sheetTemplate = new SheetTemplate();
		sheetTemplate.setParseMode(ParseMode.colAfterCol);
		sheetTemplate.setSheetName("农民专业合作组织情况统计报表");
		sheetTemplate.setTemplateRow(3);
		sheetTemplate.setTemplate(new FileSystemResource("d:/template_col.xls").getInputStream());

//		processor.process(sheetTemplate, dataContext, null);
		
	}
	
//	@Test
	public void testExportRow() throws Exception {
		List<Map<String, Object>> list = jdbcTemplate
				.queryForList("select top 10 * from STAT_COOP_REPORT a,STAT_COOP_REPORT_1_BASE b,STAT_COOP_REPORT_2_ORG c "
						+ " where a.report_id =b.report_id and a.report_id=c.report_id and district_code='310114000000'");

		DefaultExportProcessor processor = new DefaultExportProcessor();

		ListDataContext<Map<String, Object>> dataContext = new ListDataContext<Map<String, Object>>(
				list);
		
		dataContext.putData("Util", new Util());
		dataContext.putData(DefaultExportProcessor.OUT_STREAM_STORE_KEY,
				new FileOutputStream("d:/output_row.xls"));

		SheetTemplate sheetTemplate = new SheetTemplate();
		sheetTemplate.setParseMode(ParseMode.rowAfterRow);
		sheetTemplate.setSheetName("Sheet1");
		sheetTemplate.setTemplateRow(0);
		sheetTemplate.setTemplate(new FileSystemResource("d:/template_row.xls")
				.getInputStream());

//		processor.process(sheetTemplate, dataContext, null);
	}
	
//	@Test
	public void testExportCoop() throws Exception {
		String sql = "SELECT TOP 10 DISTRICT.area_name districtName,"
				+ " TOWN.area_name townName,                                                "
				+ " VILLAGE.area_name villageName,                                          "
				+ " COOP.COOP_NAME coopName,                                                "
				+ " COOP.COOP_CODE coopCode,                                                "
				+ " P.P_CONTACT_PHONE phone,                                                "
				+ " P.P_CONSTRUCTION_CONTENT building,                                      "
				+ " CAST(D.AMOUNT*10000 AS NUMERIC(18,2)) amount                                                         "
				+ "  FROM dbo.COOP_PROJECT P                                                "
				+ " INNER JOIN SYS_AREA DISTRICT ON DISTRICT.area_code = P.P_DISTRICT_CODE  "
				+ " INNER JOIN SYS_AREA TOWN ON TOWN.area_code = P.P_TOWN_CODE              "
				+ " INNER JOIN SYS_AREA VILLAGE ON VILLAGE.area_code = P.P_VILLAGE_CODE     "
				+ " INNER JOIN COOP_COOPERATION COOP ON COOP.ID = P.C_ID                    "
				+ " LEFT JOIN (SELECT P_ID, SUM(CITY_SUPP_AMOUNT) AMOUNT FROM PROJECT_DETAIL GROUP BY P_ID) D ON D.P_ID = P.ID";
//		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		
		List<Map<String, Object>> list2 = new ArrayList<Map<String,Object>>();
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("dongbz", "dongbzss");
		args.put("dongbz2", "dongbzs2");
		list2.add(args);
		list2.add(args);
		
		DefaultExportProcessor processor = new DefaultExportProcessor();

		ListDataContext<Map<String, Object>> dataContext = 
				new ListDataContext<Map<String, Object>>(list2);
		
		Order order = new Order();
		order.setRma("1641");
		dataContext.putData("order", order);
		dataContext.putData(DefaultExportProcessor.OUT_STREAM_STORE_KEY,
				new FileOutputStream("d:/output_row.xls"));

		SheetTemplate sheetTemplate = new SheetTemplate();
		sheetTemplate.setParseMode(ParseMode.rowAfterRow);
		sheetTemplate.setSheetName("DO");
		sheetTemplate.setTemplateRow(0);
		sheetTemplate.setTemplate(new FileSystemResource("d:/template_col.xls")
				.getInputStream());
//		sheetTemplate.setTemplate(new FileSystemResource("d:/合作社贷款贴息.xls")
//		.getInputStream());
		
		ExportProcessor.ResultHolder holder = null;
		
		processor.process(sheetTemplate, dataContext, holder);
		
		sheetTemplate.getTemplate().close();
		OutputStream bos = (OutputStream) dataContext.getData(DefaultExportProcessor.OUT_STREAM_STORE_KEY);
		bos.flush();
		bos.close();
	}
	
	@Test
	public void exportRedo() throws Exception {
		String fileName = "C:\\Users\\don\\Desktop\\temp\\abc.doc";
		String template = "Config/template/template_redo.vm";
		Map<String, Object> params = new HashMap<String, Object>();
		EDto dto = new EDto();
		dto.setNum("1111");
		params.put("dto", dto);
		
		OrderDo orderDo = new OrderDo();
		orderDo.setDoRma("201502272122");
		orderDo.setDoTime(new Date());
		params.put("orderDo", orderDo);
		
		Customer customer = new Customer();
		customer.setFullName("上海农业信息有限公司");
		customer.setShortName("农信");
		customer.setAddr("长宁区金钟路999号");
		params.put("customer", customer);
		
		params.put("okCount", 2);
		params.put("ngCount", 0);
		params.put("cancelCount", 0);
		
		Order order = new Order();
		order.setRma("201502272141");
		params.put("order", order);
		
		List<Sn> sns = new ArrayList<Sn>();
		Sn sn = new Sn();
		sn.setSnIndex(20);
		sn.setSn("342172205102");
		sn.setPn("201502272143");
		sn.setStopReason("22");
		sns.add(sn);
		
		sn = new Sn();
		sn.setSnIndex(20);
		sn.setSn("201502272143");
		sn.setPn("342172205102");
		sn.setStopReason("33");
		sns.add(sn);
		params.put("sns", sns);
		
		FileWriter fw = new FileWriter(fileName);
		
//		VelocityEngine velocityEngine = new VelocityEngine();
//		velocityEngine.mergeTemplate(template, "UTF-8", new VelocityContext(params, new VelocityContext()), fw);
		
		VelocityTemplateEngine velocityEngine = new cn.sh.cares.template.velocity.VelocityTemplateEngine("/");
		velocityEngine.process(template, fw, params);
		
//		Order order = new Order();
//		order.setRma("1641");
//		
//		Map beans = new HashMap();
//        beans.put("order", order);
//        XLSTransformer transformer = new XLSTransformer();
//        transformer.transformXLS("d:/template_redo.xls", beans, "d:/output_row.xls");
	}
	
}
