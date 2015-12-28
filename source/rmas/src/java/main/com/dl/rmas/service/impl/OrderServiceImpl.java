package com.dl.rmas.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.zkoss.zul.Filedownload;

import com.dl.rmas.common.enums.KeyinStatus;
import com.dl.rmas.common.enums.ReceiveStatus;
import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.common.utils.DateUtils;
import com.dl.rmas.common.utils.HeaderFooter;
import com.dl.rmas.common.utils.PropertiesUtils;
import com.dl.rmas.dao.OrderDao;
import com.dl.rmas.dto.OrderTrackDto;
import com.dl.rmas.entity.Customer;
import com.dl.rmas.entity.DictCode;
import com.dl.rmas.entity.Order;
import com.dl.rmas.entity.Product;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.entity.User;
import com.dl.rmas.entity.Order.RmaDoStatus;
import com.dl.rmas.service.CustomerService;
import com.dl.rmas.service.DictTypeCodeService;
import com.dl.rmas.service.OrderService;
import com.dl.rmas.service.ProductService;
import com.dl.rmas.service.SnService;
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

@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private SnService snService;
	@Autowired
	private ProductService productService;
	@Autowired
	private DictTypeCodeService dictTypeCodeService;
	
	@Override
	public Order queryOrderByRma(String rma) {
		Order queryOrder = new Order();
		queryOrder.setRma(rma);

		List<Order> orders = queryOrdersByQueryDto(queryOrder, null);
		if (orders.size() == 1) {
			return orders.get(0);
		}
		
		return null;
	}
	
	@Override
	public String queryMaxRmaByCustomerId(Integer customerId) {
		return orderDao.findMaxRmaByCustomerId(customerId);
	}
	
	@Override
	public List<Order> queryOrdersByQueryDto(Order queryOrder, PagingDto pagingDto) {
		return orderDao.findOrdersByQueryDto(queryOrder, pagingDto);
	}
	
	@Override
	public List<OrderTrackDto> queryTrackByQueryDto(Order queryOrder,
			PagingDto pagingDto) {
		return orderDao.findTrackByQueryDto(queryOrder, pagingDto);
	}
	
	@Override
	public void doCreateOrder(Order order) {
		Assert.notNull(order);
		
		order.setRma(buildOrderRma(order.getCustomerId()));
		order.setReceiveStatus(ReceiveStatus.WAIT_RECEIVE);
		order.setReceiver(currentUserId());
		order.setReceiveTime(currentTime());
		order.setKeyinStatus(KeyinStatus.OPEN);
		order.setDoStatus(RmaDoStatus.ONLINE);
		order.setValidity(Validity.VALID);
		doSave(order);
	}
	
	@Override
	public void doModifyOrder(Order order) {
		order.setLastModifier(currentUserId());
		order.setLastModifyTime(currentTime());
		doUpdate(order);
	}
	
	@Override
	public void doFinishKeyin(Order order) throws Exception {
		order.setKeyinStatus(KeyinStatus.CLOSE);
		
		String receivedPdfPath = generateReceivedPDF(order);
		order.setReceivedPdfPath(receivedPdfPath);
		
		Filedownload.save(new File(receivedPdfPath), "application/x-download");
		
		order.setLastModifier(currentUserId());
		order.setLastModifyTime(currentTime());
		orderDao.getHibernateTemplate().merge(order);
	}
	
	@Override
	public void doExportTrack(Order query) throws Exception {
		List<OrderTrackDto> tracks = queryTrackByQueryDto(query, null);
		
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("tracks", tracks);
		String path = "/data/rmas/ReportForms/";
		
		// 1）生成EXCEL
		XLSTransformer transformer = new XLSTransformer();
		ClassPathResource cpr = new ClassPathResource("/Config/template/template_order_track.xls");
		Workbook wb = transformer.transformXLS(cpr.getInputStream(), beans);
		
		File fold = new File(path);
		if (!fold.isDirectory()) {
			fold.mkdirs();
		}
		
		String excelFileName = "RMA Track_" + DateUtils.transferDate2Str(new Date(), "yyyyMMddHHmmss") + ".xls";
		OutputStream os = new FileOutputStream(new File(path + excelFileName));
		wb.write(os);
		os.flush();
		os.close();
		
		Filedownload.save(new File(path + excelFileName), "application/x-download");
	}
	
	@Override
	public void doDoOrder(Integer orderId) {
		Order order = queryById(Order.class, orderId);
		order.setDoStatus(RmaDoStatus.DONE);
		doModifyOrder(order);
	}
	
	@Override
	public void doExportReceive(Order query) throws Exception {
		List<Order> result = queryOrdersByQueryDto(query, null);
		
		for (Order order : result) {
			order.setCompanyName(queryById(Customer.class, order.getCustomerId()).getFullName());
			order.setReceiverName(queryById(User.class, order.getReceiver()).getUserName());
		}
		
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("result", result);
		String path = "/data/rmas/Produce/";
		
		// 1）生成EXCEL
		XLSTransformer transformer = new XLSTransformer();
		ClassPathResource cpr = new ClassPathResource("/Config/template/template_receive.xls");
		Workbook wb = transformer.transformXLS(cpr.getInputStream(), beans);
		
		File fold = new File(path);
		if (!fold.isDirectory()) {
			fold.mkdirs();
		}
		
		String excelFileName = "Receive_" + DateUtils.transferDate2Str(new Date(), "yyyyMMddHHmmss") + ".xls";
		OutputStream os = new FileOutputStream(new File(path + excelFileName));
		wb.write(os);
		os.flush();
		os.close();
		
		Filedownload.save(new File(path + excelFileName), "application/x-download");
		
	}
	
	/**
	 * 生成RMA编号
	 *
	 * @param order
	 * @return
	 */
	private String buildOrderRma(Integer costomerId) {
		StringBuffer orderRma = new StringBuffer();
		
		orderRma.append(PropertiesUtils.getValueInSystem("com.id"));
		
		Customer customer = customerService.queryById(Customer.class, costomerId);
		orderRma.append(customer.getShortName());
		orderRma.append(DateUtils.formateToYYMMDD(currentTime()));
		
		String maxRma = queryMaxRmaByCustomerId(costomerId);
		if (StringUtils.isBlank(maxRma)) {
			orderRma.append("0001");
		} else {
			int maxIndex = Integer.parseInt(maxRma.substring(maxRma.length() - 4));
			orderRma.append(StringUtils.leftPad(String.valueOf(maxIndex + 1), 4, "0"));
		}
		
		return orderRma.toString();
	}
	
	private String generateReceivedPDF(Order order) throws Exception {
		// 计算总页数
		List<Sn> sns = snService.queryAllSnsByRma(order.getOrderId());
		int totalPageNumber;	// 总页数
		int num = sns.size();
		if (num <= 42) {
			totalPageNumber = 1;
		} else if (num >= 43 && num <= 49) {
			totalPageNumber = 2;
		} else {
			int zy = (num-49)%60;
			if (zy >= 55 || zy == 0) {
				totalPageNumber = 2 + (zy==0?(num-49)/60:(num-49)/60+ 1);
			} else {
				totalPageNumber = ((num+17)%60==0) ? (num+17)/60 : (num+17)/60+1;		
			}
		}	
		
		BaseFont bf = BaseFont.createFont(BaseFont.COURIER, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED); 
		Font bold = new Font(bf, 11, Font.BOLD);
		Font normal = new Font(bf, 8, Font.NORMAL);
		
		String path = "/data/rmas/received/"; 
		File fold = new File(path);
		if (!fold.isDirectory()) {
			fold.mkdirs();
		}
		
		String name = path + "received_" + order.getRma() + ".pdf";
		FileOutputStream out = new FileOutputStream(name);
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
		document.add(new Paragraph("\n"));
		
		PdfPCell cell;
		
		float[] descWidths = {0.6f, 0.4f};
		PdfPTable desc = new PdfPTable(descWidths);
		desc.setWidthPercentage(100);
		cell = new PdfPCell(new Phrase(PropertiesUtils.getValueInSystem("jlan"), normal));
		cell.setBorder(Rectangle.NO_BORDER);
		desc.addCell(cell);
		cell = new PdfPCell(new Phrase("RECEIVED", FontFactory.getFont(BaseFont.COURIER_BOLD, 20)));
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
		
		float[] shipWidths = {0.1f, 0.03f, 0.31f, 0.24f, 0.03f, 0.28f};
		PdfPTable ship = new PdfPTable(shipWidths);
		ship.setWidthPercentage(100);
		cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(3);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase("RMA No.", bold));
		cell.setBorder(Rectangle.NO_BORDER);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase(":", bold));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase(order.getRma(), bold));
		cell.setBorder(Rectangle.NO_BORDER);
		ship.addCell(cell);
		cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(3);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase("Customer Order No.", bold));
		cell.setBorder(Rectangle.NO_BORDER);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase(":", bold));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		ship.addCell(cell);
		cell = new PdfPCell(new Phrase(order.getCustrma(), bold));
		cell.setBorder(Rectangle.NO_BORDER);
		ship.addCell(cell);
		document.add(ship);
		
		PdfPTable sn = new PdfPTable(4);
		int headerWidths[] = {10, 15, 25, 50}; // percentage
		sn.setWidths(headerWidths);
		sn.setWidthPercentage(100);
		sn.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		sn.addCell(new Phrase("No.", normal));
		sn.addCell(new Phrase("Category", normal));
		sn.addCell(new Phrase("P/N", normal));
		sn.addCell(new Phrase("Serial Number", normal));
		
		Sn vo;
		Product product;
		DictCode dictCode;
		for (int i = 0; i < sns.size(); i++) {
			vo = sns.get(i);
			product = productService.queryById(Product.class, vo.getProductId());
			dictCode = dictTypeCodeService.queryById(DictCode.class, product.getProductType());
			cell = new PdfPCell(new Phrase(i + 1 + "", normal));
			cell.setBorder(Rectangle.LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			sn.addCell(cell);
			cell = new PdfPCell(new Phrase(dictCode != null ? dictCode.getCodeName() : "", normal));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			sn.addCell(cell);
			cell = new PdfPCell(new Phrase(product.getPn(), normal));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			sn.addCell(cell);
			cell = new PdfPCell(new Phrase(vo.getSn(), normal));
			cell.setBorder(Rectangle.RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			sn.addCell(cell);
		}
		if (num <= 42) {
			for (int i = 0; i < 42 - num; i++) {
				cell = new PdfPCell(new Phrase(" ", normal));
				cell.setBorder(Rectangle.LEFT);
				sn.addCell(cell);
				cell = new PdfPCell(new Phrase(" ", normal));
				cell.setColspan(2);
				cell.setBorder(Rectangle.NO_BORDER);
				sn.addCell(cell);
				cell = new PdfPCell(new Phrase(" ", normal));
				cell.setBorder(Rectangle.RIGHT);
				sn.addCell(cell);
			}
		} else if (num >= 43 && num <= 49) {
			for (int i = 0; i < 49-num+60-6; i++) {
				cell = new PdfPCell(new Phrase(" ", normal));
				cell.setBorder(Rectangle.LEFT);
				sn.addCell(cell);
				cell = new PdfPCell(new Phrase(" ", normal));
				cell.setColspan(2);
				cell.setBorder(Rectangle.NO_BORDER);
				sn.addCell(cell);
				cell = new PdfPCell(new Phrase(" ", normal));
				cell.setBorder(Rectangle.RIGHT);
				sn.addCell(cell);
			}
		} else if (num > 49) {
			int zy = (num-49)%60;
			if (zy >= 55 || zy == 0) {
				for (int i = 0; i < (zy == 0 ? (60-6) : (60-zy+60-6)); i++) {
					cell = new PdfPCell(new Phrase(" ", normal));
					cell.setBorder(Rectangle.LEFT);
					sn.addCell(cell);
					cell = new PdfPCell(new Phrase(" ", normal));
					cell.setColspan(2);
					cell.setBorder(Rectangle.NO_BORDER);
					sn.addCell(cell);
					cell = new PdfPCell(new Phrase(" ", normal));
					cell.setBorder(Rectangle.RIGHT);
					sn.addCell(cell);
				}
			} else {
				for (int i = 0; i < (60-6-(num-49)%60); i++) {
					cell = new PdfPCell(new Phrase(" ", normal));
					cell.setBorder(Rectangle.LEFT);
					sn.addCell(cell);
					cell = new PdfPCell(new Phrase(" ", normal));
					cell.setColspan(2);
					cell.setBorder(Rectangle.NO_BORDER);
					sn.addCell(cell);
					cell = new PdfPCell(new Phrase(" ", normal));
					cell.setBorder(Rectangle.RIGHT);
					sn.addCell(cell);
				}				
			}
		}				
		
		document.add(sn);
		sn = new PdfPTable(3);
		int _headerWidths[] = {33, 34, 33}; // percentage
		sn.setWidths(_headerWidths);
		sn.setWidthPercentage(100);
		sn.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		
		cell = new PdfPCell(new Phrase("Receive    By,", normal));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.TOP);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("Check    By,", normal));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.TOP);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("Delivery    By,", normal));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.TOP);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("\n", normal));
		cell.setColspan(3);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("----------------", normal));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("----------------", normal));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("----------------", normal));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("Warehouse", normal));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("Service Center", normal));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		Customer customer = customerService.queryById(Customer.class, order.getCustomerId());
		cell = new PdfPCell(new Phrase(customer.getFullName(), normal));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("\n", normal));
		cell.setColspan(3);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		cell = new PdfPCell(new Phrase("White : Customer          Red : Service Center          Yellow : Warehouse", normal));
		cell.setColspan(3);
		cell.setBorder(Rectangle.NO_BORDER);
		sn.addCell(cell);
		
		document.add(sn);
		
		document.close();		
		
		return name;
	}

}
