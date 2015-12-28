package com.dl.rmas.web.vm.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.dl.core.jxls.common.model.ParameterSet;
import com.dl.core.jxls.entity.FileSave;
import com.dl.core.jxls.entity.ReportConfig;
import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.parse.ExcelTemplateParser.ParseMode;
import com.dl.core.jxls.parse.ReportConfigSupportParser;
import com.dl.core.jxls.parse.model.TableModel;
import com.dl.core.jxls.service.ReportConfigService;
import com.dl.core.jxls.service.store.DataStoreService;
import com.dl.core.jxls.validation.ValidateService;
import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.common.utils.PropertiesUtils;
import com.dl.rmas.common.utils.SecurityUtils;
import com.dl.rmas.entity.Product;
import com.dl.rmas.service.LabelValueBeanService;
import com.dl.rmas.service.ProductService;
import com.dl.rmas.web.zkmodel.LabelValueBean;

public class ProductQueryVM extends PageVM {

	public static final String KEY_PRODUCT = "product";
	public static final String KEY_PRODUCT_ID = "productId";
	
	@WireVariable
	private LabelValueBeanService labelValueBeanService;
	@WireVariable
	private ProductService productService;
	@WireVariable
	private ReportConfigService reportConfigService;
	@WireVariable
	private ValidateService validateService;
	@WireVariable
	private DataStoreService dataStoreService;
	
	private List<LabelValueBean<Integer>> productTypeLabelValueBeans;
	private Product product;
	private List<Product> result;
	private Product selected;
	
	@Init
	public void init() {
		productTypeLabelValueBeans = labelValueBeanService.buildByDictTypeIdWithSelect(Constants.TYPE_ID_PRODUCT_TYPE);
		
		product = new Product();
		product.setValidity(Validity.VALID);
		
		onSearch();
	}

	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSearch() {
		result = productService.queryProductsByQueryDto(product, pagingDto);
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onShowAdd() {
		showModal(ProductEditVM.URL_PRODUCT_EDIT);
		
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onShowEdit() {
		if (selected == null) {
			showWarningBox(PropertiesUtils.getValueInSystem("product.query.validate"));
			return;
		}
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(ProductQueryVM.KEY_PRODUCT, selected);
		showModal(ProductEditVM.URL_PRODUCT_EDIT, args);
	}
	
	@Command
	public void onImport(@BindingParam("media") Media media) {
		if (!showQuestionBox(PropertiesUtils.getValueInSystem("product.query.confirm.f") 
				+ selected.getPn() 
				+ PropertiesUtils.getValueInSystem("product.query.confirm.s"))) {
			return;
		}
		
		if (media == null) {
			return;
		}
		
		FileSave fileEntity = new FileSave();
		fileEntity.setFileName("BOM LIST");
		fileEntity.setReportType("ZZHXX");
		fileEntity.setUploadTime(new Date());
		fileEntity.setUserId(SecurityUtils.getCurrentUser().getUserId().toString());
		productService.doSave(fileEntity);
		
		ReportConfig configBase = reportConfigService.findByReportType("ZZHXX");
		
		ReportConfigSupportParser excelParser = new ReportConfigSupportParser();
		excelParser.setStartRow(configBase.getStartRow());
		excelParser.setEndRow(configBase.getEndRow());
		excelParser.setStartCol(configBase.getStartCol());
		excelParser.setEndCol(configBase.getEndCol());// 由ReportConfigSupportParser来决定何时结束
		excelParser.setReportConfig(configBase);
		excelParser.setNeedParseComment(false);// 不解析标注信息
		excelParser.setNeedParseStyle(false);// 不解析样式信息
		excelParser.setParseMode(ParseMode.rowAfterRow);// 按行进行解析
		excelParser.setSheetName(configBase.getSheetName());
		
		excelParser.setResource(media.getStreamData());
		
		TableModel table = excelParser.parseTemplate();
		ParameterSet params = new ParameterSet();
		params.setParameter("batchId", fileEntity.getId());
		
		UploadDataModel dataBase = validateService.processAndValidate(configBase, table, params);
		
		// 校验。假如失败返回失败信息，而且退出导入流程
		StringBuilder errorMessage = new StringBuilder();
		for (RowDataModel row : dataBase.getRowDatas()) {
			if (!row.getValidateResult().isSuccess()) {
				errorMessage.append(PropertiesUtils.getValueInSystem("product.query.validate.f") 
						+ row.getRow() 
						+ PropertiesUtils.getValueInSystem("product.query.validate.s"));
				
				for (CellDataModel cell : row.getCellDatas()) {
					if (!cell.getValidateResult().isSuccess()) {
						errorMessage.append(cell.getStrValue() + "：" + cell.getValidateResult().getMsg() + "\n");
					}
				}
			}
		}
		if (StringUtils.isNotBlank(errorMessage.toString())) {
			showErrorBox(errorMessage.toString());
			return;
		}
		
		for (RowDataModel row : dataBase.getRowDatas()) {
			for (CellDataModel cell : row.getCellDatas()) {
				if ("product_id".equals(cell.getColumnName())) {
					cell.setValue(selected.getProductId());
					continue;
				}
				if ("creater".equals(cell.getColumnName())) {
					cell.setValue(SecurityUtils.getCurrentUser().getUserId());
					continue;
				}
				if ("create_time".equals(cell.getColumnName())) {
					cell.setValue(new Date());
					continue;
				}
				if ("validity".equals(cell.getColumnName())) {
					cell.setValue(Validity.VALID.name());
					continue;
				}				
			}
		}
		
		dataStoreService.storeData(dataBase, params);
		
		onView(selected.getProductId());
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onDelete() {
		if (!showQuestionBox(Constants.CONFIRM_TO_DELETE)) {
			return;
		}
		
		productService.doRemove(selected);
		
		showInformationBox(Constants.OPERATION_COMPLETED);
		onSearch();
	}	
	
	/**
	 * 查看BOM LIST
	 */
	@Command
	public void onView(@BindingParam("productId")Integer productId) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(KEY_PRODUCT_ID, productId);
		showModal(BomListViewVM.URL_BOM_LIST_VIEW, args);
	}
	
	public List<LabelValueBean<Integer>> getProductTypeLabelValueBeans() {
		return productTypeLabelValueBeans;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getResult() {
		return result;
	}

	public Product getSelected() {
		return selected;
	}

	public void setSelected(Product selected) {
		this.selected = selected;
	}
	
}
