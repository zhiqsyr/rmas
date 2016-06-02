package com.dl.rmas.web.vm.system;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Window;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.common.utils.PropertiesUtils;
import com.dl.rmas.entity.Product;
import com.dl.rmas.service.LabelValueBeanService;
import com.dl.rmas.service.ProductService;
import com.dl.rmas.web.zkmodel.LabelValueBean;

public class ProductEditVM extends BaseVM {

	public static final String URL_PRODUCT_EDIT = "/zul/system/product_edit.zul";

	@WireVariable
	private LabelValueBeanService labelValueBeanService;
	@WireVariable
	private ProductService productService;
	
	private List<LabelValueBean<Integer>> productTypeLabelValueBeans;
	private Product product;
	
	@Wire
	private Window editWin;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		productTypeLabelValueBeans = labelValueBeanService.buildByDictTypeIdWithSelect(Constants.TYPE_ID_PRODUCT_TYPE);
		
		product = getArgValue(Product.class, ProductQueryVM.KEY_PRODUCT);
		if (product == null) {
			product = new Product();
		}
	}

	@Command
	public void onSubmit() {
		if (product.getProductType() == null) {
			showWarningBox(PropertiesUtils.getValueInSystem("product.edit.validate"));
			return;
		}
		
		productService.doCreateOrModifyProduct(product);
		
		editWin.setAttribute(ProductQueryVM.KEY_PRODUCT, product);
		editWin.detach();
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
	
}
