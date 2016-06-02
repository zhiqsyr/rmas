package com.dl.rmas.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.zkoss.zul.Filedownload;

import com.dl.rmas.common.enums.FinalResult;
import com.dl.rmas.common.utils.DateUtils;
import com.dl.rmas.common.utils.HeaderFooter;
import com.dl.rmas.common.utils.PropertiesUtils;
import com.dl.rmas.dao.OrderDoDao;
import com.dl.rmas.entity.Customer;
import com.dl.rmas.entity.DictCode;
import com.dl.rmas.entity.OrderDo;
import com.dl.rmas.entity.Product;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.service.CustomerService;
import com.dl.rmas.service.DictTypeCodeService;
import com.dl.rmas.service.OrderDoService;
import com.dl.rmas.service.ProductService;
import com.dl.rmas.web.zkmodel.PagingDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service("orderDoService")
public class OrderDoServiceImpl extends BaseServiceImpl implements OrderDoService {

	@Autowired
	private OrderDoDao orderDoDao;
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProductService productService;
	@Autowired
	private DictTypeCodeService dictTypeCodeService;
	
	@Override
	public OrderDo doCreateAndExportOrderDo(Integer orderId, List<Sn> selecteds) throws Exception {
//		Order order = orderService.queryById(Order.class, orderId);
		Customer customer = customerService.queryById(Customer.class, selecteds.get(0).getOrder().getCustomerId());
		
		OrderDo orderDo = new OrderDo();
		orderDo.setDoRma(buildOrderDoRma(customer));
//		orderDo.setOrderId(orderId);
		orderDo.setCustomerId(customer.getCustomerId());
		orderDo.setDor(currentUserId());
		orderDo.setDoTime(currentTime());
		
		
		// 生成EXCEL与WORD，并提示下载
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("jlan", PropertiesUtils.getValueInSystem("jlan"));
		beans.put("blok", PropertiesUtils.getValueInSystem("blok.no"));
		beans.put("area", PropertiesUtils.getValueInSystem("area"));
		beans.put("tlp", PropertiesUtils.getValueInSystem("tlp"));
//		beans.put("order", order);
		beans.put("orderDo", orderDo);
		beans.put("customer", customer);
		
		Product pdt;
		DictCode dictCode;
		Integer okCount = 0, ngCount = 0, cancelCount = 0, cidCount = 0;	// 统计
		for (Sn sn : selecteds) {
			if (FinalResult.OK.equals(sn.getFinalResult())) {
				okCount ++;
			} else if (FinalResult.NG.equals(sn.getFinalResult())) {
				ngCount ++;
			} else if (FinalResult.CANCEL.equals(sn.getFinalResult())) {
				cancelCount ++;
			} else if (FinalResult.CID.equals(sn.getFinalResult())) {
				cidCount ++;
			}
			
			if (sn.getProductId() != null) {
				pdt = productService.queryById(Product.class, sn.getProductId());
				sn.setPn(pdt != null ? pdt.getPn() : null);
				sn.setPcbType(pdt != null ? pdt.getPcbType() : null);
			}
			if (sn.getCustomerFaultDesc() != null) {
				dictCode = dictTypeCodeService.queryById(DictCode.class, sn.getCustomerFaultDesc());
				sn.setFaultCode(dictCode != null ? dictCode.getCodeName() : null);
			}
		}
		
		Collections.sort(selecteds);			// 排列 sns 
		beans.put("sns", selecteds);
		beans.put("okCount", okCount);
		beans.put("ngCount", ngCount);
		beans.put("cancelCount", cancelCount);
		beans.put("cidCount", cidCount);
		
		String path = "/data/rmas/redo/";
		
		// 1）生成EXCEL
		XLSTransformer transformer = new XLSTransformer();
		ClassPathResource cpr = new ClassPathResource("/Config/template/template_redo.xls");
		Workbook wb = transformer.transformXLS(cpr.getInputStream(), beans);
		
		File fold = new File(path);
		if (!fold.isDirectory()) {
			fold.mkdirs();
		}
		
		String excelFileName = "redo_" + orderDo.getDoRma() + ".xls";
		OutputStream os = new FileOutputStream(new File(path + excelFileName));
		wb.write(os);
		os.flush();
		os.close();
    
		// 2）生成WORD
//		String wordFileName = "redo_" + orderDo.getDoRma() + ".doc";
//		FileWriter fw = new FileWriter(path + wordFileName);
//		VelocityTemplateEngine velocityEngine = new VelocityTemplateEngine("/");
//		velocityEngine.process("/Config/template/template_redo.vm", fw, beans);
		
		// Word --> PDF
		String pdfFileName = "redo_" + orderDo.getDoRma() + ".pdf";
		generateDoPdf(path + pdfFileName, beans);
		
        Filedownload.save(new File(path + excelFileName), "application/x-download");
//        Filedownload.save(new File(path + pdfFileName), "application/x-download");
		
        orderDo.setExcelPath(path + excelFileName);
        orderDo.setWordPath(path + pdfFileName);
		doSave(orderDo);
		return orderDo;
	}
	
	@Override
	public String queryMaxRmaByCustomerId(Integer customerId) {
		return orderDoDao.findMaxRmaByCustomerId(customerId);
	}
	
	@Override
	public List<OrderDo> queryOrderDosByQueryDto(OrderDo query,
			PagingDto pagingDto) {
		return orderDoDao.findOrderDosByQueryDto(query, pagingDto);
	}
	
	/**
	 * 生成RMA编号
	 *
	 * @param order
	 * @return
	 */
	private String buildOrderDoRma(Customer customer) {
		StringBuffer rma = new StringBuffer();
		
		rma.append(PropertiesUtils.getValueInSystem("com.id"));
		
		rma.append(customer.getShortName());
		rma.append("DO");
		rma.append(DateUtils.formateToYYMMDD(currentTime()));
		
		String maxRma = queryMaxRmaByCustomerId(customer.getCustomerId());
		if (StringUtils.isBlank(maxRma)) {
			rma.append("0001");
		} else {
			int maxIndex = Integer.parseInt(maxRma.substring(maxRma.length() - 4));
			rma.append(StringUtils.leftPad(String.valueOf(maxIndex + 1), 4, "0"));
		}
		
		return rma.toString();
	}

	private void generateDoPdf(String pdfPath, Map<String, Object> beans) throws Exception {
//		Order order = (Order) beans.get("order");
		OrderDo orderDo = (OrderDo) beans.get("orderDo");
		Customer customer = (Customer) beans.get("customer");
		List<Sn> sns = (List<Sn>) beans.get("sns");
		Integer okCount = Integer.parseInt(beans.get("okCount").toString()),
				ngCount = Integer.parseInt(beans.get("ngCount").toString()),
				cancelCount = Integer.parseInt(beans.get("cancelCount").toString()),
				cidCount = Integer.parseInt(beans.get("cidCount").toString());
		
		// 计算总页数
		int totalPageNumber;	// 总页数
		int num = sns.size();
		if (num <= 34) {
			totalPageNumber = 1;
		} else if (num >= 35 && num <= 44) {
			totalPageNumber = 2;
		} else {
			int zy = (num-44)%60;
			if (zy >= 51 || zy == 0) {
				totalPageNumber = 2 + (zy==0?(num-44)/60:(num-44)/60+1);
			} else {
				totalPageNumber = ((num+26)%60==0) ? (num+26)/60 : (num+26)/60+1;		
			}
		}			
		
		BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED); 
		Font bold = new Font(bf, 11, Font.BOLD);
		Font normal = new Font(bf, 8, Font.NORMAL);
		
		FileOutputStream out = new FileOutputStream(pdfPath);
		Rectangle pageSize = new Rectangle(612, 792);
		pageSize.rotate();
		Document document = new Document(pageSize);
		PdfWriter writer = PdfWriter.getInstance(document, out);
//		float width = document.getPageSize().getWidth();
		writer.setBoxSize("art", pageSize);
        HeaderFooter header = new HeaderFooter();
        header.setTotalPageNumber(totalPageNumber);
        writer.setPageEvent(header);
		document.open();
		
		document.add(new Paragraph("PT. INDO RAYA LESTARI", FontFactory.getFont(BaseFont.COURIER_BOLD, 15)));
		document.add(new Paragraph("\n", normal));
		
		PdfPCell cell;
		
		float[] descWidths = {0.6f, 0.4f};
		PdfPTable desc = new PdfPTable(descWidths);
		desc.setWidthPercentage(100);
		cell = new PdfPCell(new Phrase(PropertiesUtils.getValueInSystem("jlan"), normal));
		cell.setBorder(Rectangle.NO_BORDER);
		desc.addCell(cell);
		cell = new PdfPCell(new Phrase("DELIVERY ORDER", FontFactory.getFont(BaseFont.COURIER_BOLD, 20)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingTop(14f);
		cell.setRowspan(4);
		desc.addCell(cell);
		cell = new PdfPCell(new Phrase(PropertiesUtils.getValueInSystem("blok.no"), normal));
		cell.setBorder(Rectangle.NO_BORDER);
		desc.addCell(cell);
		cell = new PdfPCell(new Phrase(PropertiesUtils.getValueInSystem("area"), normal));
		cell.setBorder(Rectangle.NO_BORDER);
		desc.addCell(cell);
		cell = new PdfPCell(new Phrase(PropertiesUtils.getValueInSystem("tlp"), normal));
		cell.setBorder(Rectangle.NO_BORDER);
		desc.addCell(cell);
		document.add(desc);
		
		float[] shipWidths = {0.10f, 0.03f, 0.47f, 0.14f, 0.03f, 0.23f};
		PdfPTable ship = new PdfPTable(shipWidths);
		ship.setWidthPercentage(100);
		cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(3);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase("DO No.", bold));
		cell.setBorder(Rectangle.NO_BORDER);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase(":", bold));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase(orderDo.getDoRma(), bold));
		cell.setBorder(Rectangle.NO_BORDER);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase("Ship To", normal));
		cell.disableBorderSide(2);
		cell.disableBorderSide(8);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase(":", normal));
        cell.setBorder(Rectangle.TOP);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        ship.addCell(cell);
		cell = new PdfPCell(new Phrase(customer.getFullName(), normal));
		cell.disableBorderSide(2);
		cell.disableBorderSide(4);
        ship.addCell(cell);
		cell = new PdfPCell(new Phrase("Customer ID", normal));
        cell.setBorder(Rectangle.TOP);
        ship.addCell(cell);
		cell = new PdfPCell(new Phrase(":", normal));
        cell.setBorder(Rectangle.TOP);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        ship.addCell(cell);
		cell = new PdfPCell(new Phrase(customer.getShortName(), normal));
		cell.disableBorderSide(2);
		cell.disableBorderSide(4);
        ship.addCell(cell);
        cell = new PdfPCell();
        cell.setRowspan(2);
        cell.setColspan(2);
        cell.setBorder(Rectangle.LEFT);
        ship.addCell(cell);
        cell = new PdfPCell(new Phrase(customer.getAddrFormatted(), normal));
        cell.setRowspan(2);
        cell.setBorder(Rectangle.RIGHT);
        cell.setNoWrap(false);
        ship.addCell(cell);
        cell = new PdfPCell(new Phrase("DO Date", normal));
		cell.setBorder(Rectangle.NO_BORDER);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase(":", normal));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase(orderDo.getDoTimeFormatted(), normal));
		cell.setBorder(Rectangle.RIGHT);
		ship.addCell(cell);
        cell = new PdfPCell(new Phrase("Ship Via", normal));
		cell.setBorder(Rectangle.NO_BORDER);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase(":", normal));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase(customer.getShipVia(), normal));
		cell.setBorder(Rectangle.RIGHT);
		ship.addCell(cell);
        cell = new PdfPCell(new Phrase("Attn", normal));
		cell.disableBorderSide(1);
		cell.disableBorderSide(8);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase(":", normal));
		cell.setBorder(Rectangle.BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase(customer.getAttn(), normal));
		cell.disableBorderSide(1);
		cell.disableBorderSide(4);
		ship.addCell(cell);
        cell = new PdfPCell(new Phrase("Ship Date", normal));
		cell.setBorder(Rectangle.BOTTOM);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase(":", normal));
		cell.setBorder(Rectangle.BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase(orderDo.getDoTimeFormatted(), normal));
		cell.disableBorderSide(1);
		cell.disableBorderSide(4);
		ship.addCell(cell);
		document.add(ship);
		
		document.add(new Paragraph("\n"));
		
		PdfPTable sn = new PdfPTable(7);
		int headerWidths[] = {5, 18, 10, 24, 17, 12, 17}; // percentage
		sn.setWidths(headerWidths);
		sn.setWidthPercentage(100);
		sn.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		sn.addCell(new Phrase("No.", normal));
		sn.addCell(new Phrase("RMA No.", normal));
		sn.addCell(new Phrase("No. Urut IRL", normal));
		sn.addCell(new Phrase("Serial Number", normal));
		sn.addCell(new Phrase("P/N", normal));
		sn.addCell(new Phrase("Result", normal));
		sn.addCell(new Phrase("Remark", normal));
		Sn vo;
		for (int i = 0; i < sns.size(); i++) {
			vo = sns.get(i);
			cell = new PdfPCell(new Phrase(i + 1 + "", normal));
			cell.setBorder(Rectangle.LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			sn.addCell(cell);
			cell = new PdfPCell(new Phrase(vo.getRma(), normal));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			sn.addCell(cell);
			cell = new PdfPCell(new Phrase(vo.getSnIndexFormatted(), normal));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			sn.addCell(cell);
			cell = new PdfPCell(new Phrase(vo.getSn(), normal));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			sn.addCell(cell);
			cell = new PdfPCell(new Phrase(vo.getPn(), normal));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			sn.addCell(cell);
			cell = new PdfPCell(new Phrase(vo.getFinalResult() != null ? vo.getFinalResult().name() : "", normal));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			sn.addCell(cell);
			cell = new PdfPCell(new Phrase(vo.getStopReason(), normal));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.RIGHT);
			sn.addCell(cell);
		}
		
		if (num <= 34) {
			for (int i = 0; i < 34 - num; i++) {
				cell = new PdfPCell(new Phrase(" ", normal));
				cell.setBorder(Rectangle.LEFT);
				sn.addCell(cell);
				cell = new PdfPCell(new Phrase(" ", normal));
				cell.setColspan(5);
				cell.setBorder(Rectangle.NO_BORDER);
				sn.addCell(cell);
				cell = new PdfPCell(new Phrase(" ", normal));
				cell.setBorder(Rectangle.RIGHT);
				sn.addCell(cell);
			}
		} else if (num >= 35 && num <= 44) {
			for (int i = 0; i < 44-num+60-10; i++) {
				cell = new PdfPCell(new Phrase(" ", normal));
				cell.setBorder(Rectangle.LEFT);
				sn.addCell(cell);
				cell = new PdfPCell(new Phrase(" ", normal));
				cell.setColspan(5);
				cell.setBorder(Rectangle.NO_BORDER);
				sn.addCell(cell);
				cell = new PdfPCell(new Phrase(" ", normal));
				cell.setBorder(Rectangle.RIGHT);
				sn.addCell(cell);
			}
		} else if (num > 44) {
			int zy = (num-44)%60;
			if (zy >= 51 || zy == 0) {
				for (int i = 0; i < (zy == 0 ? (60-10) : (60-zy+60-10)); i++) {
					cell = new PdfPCell(new Phrase(" ", normal));
					cell.setBorder(Rectangle.LEFT);
					sn.addCell(cell);
					cell = new PdfPCell(new Phrase(" ", normal));
					cell.setColspan(5);
					cell.setBorder(Rectangle.NO_BORDER);
					sn.addCell(cell);
					cell = new PdfPCell(new Phrase(" ", normal));
					cell.setBorder(Rectangle.RIGHT);
					sn.addCell(cell);
				}
			} else {
				for (int i = 0; i < (60-10-(num-44)%60); i++) {
					cell = new PdfPCell(new Phrase(" ", normal));
					cell.setBorder(Rectangle.LEFT);
					sn.addCell(cell);
					cell = new PdfPCell(new Phrase(" ", normal));
					cell.setColspan(5);
					cell.setBorder(Rectangle.NO_BORDER);
					sn.addCell(cell);
					cell = new PdfPCell(new Phrase(" ", normal));
					cell.setBorder(Rectangle.RIGHT);
					sn.addCell(cell);
				}
			}
		}		
		
		cell = new PdfPCell(new Phrase("TOTAL : OK : "+okCount+"   NG : "
				+ngCount+"   Cancel : "+cancelCount+"   CID : "+cidCount, bold));
		cell.setColspan(7);
		cell.setBorder(Rectangle.TOP);
		sn.addCell(cell);
		
		cell = new PdfPCell(new Phrase("\n", normal));
		cell.setColspan(7);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		sn.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Check    By,", normal));
		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("Delivery    By,", normal));
		cell.setColspan(3);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("Receive    By,", normal));
		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("\n", normal));
		cell.setColspan(7);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("----------------", normal));
		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("----------------", normal));
		cell.setColspan(3);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("----------------", normal));
		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("Service Center", normal));
		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("Warehouse", normal));
		cell.setColspan(3);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);		
		cell = new PdfPCell(new Phrase(customer.getFullName(), normal));
		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("\n", normal));
		cell.setColspan(7);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		
		cell = new PdfPCell(new Phrase("White : Customer          Red : Service Center          Yellow : Warehouse", normal));
		cell.setColspan(7);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		
		document.add(sn);
		
		document.close();		
	}
	
}
