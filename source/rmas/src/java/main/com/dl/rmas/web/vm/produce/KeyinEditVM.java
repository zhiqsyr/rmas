package com.dl.rmas.web.vm.produce;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.common.enums.IF;
import com.dl.rmas.common.enums.SnStatus;
import com.dl.rmas.common.utils.PropertiesUtils;
import com.dl.rmas.entity.DictCode;
import com.dl.rmas.entity.Order;
import com.dl.rmas.entity.Product;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.entity.Sn.KeepStatus;
import com.dl.rmas.service.DictTypeCodeService;
import com.dl.rmas.service.LabelValueBeanService;
import com.dl.rmas.service.OrderService;
import com.dl.rmas.service.ProductService;
import com.dl.rmas.service.SnService;
import com.dl.rmas.web.vm.system.BaseVM;
import com.dl.rmas.web.zkmodel.LabelValueBean;

public class KeyinEditVM extends BaseVM {

	public static final String URL_KEYIN_EDIT = "/zul/produce/keyin_edit.zul";
	
	private static final String NEXT_STEP_MIDH = "MIDH";
	private static final String NEXT_STEP_L1KEYIN = "L1KEYIN";
	
	@WireVariable
	private LabelValueBeanService labelValueBeanService;
	@WireVariable
	private OrderService orderService;
	@WireVariable
	private DictTypeCodeService dictTypeCodeService;
	@WireVariable
	private ProductService productService;
	@WireVariable
	private SnService snService;
	
	private List<LabelValueBean<Integer>> customerLabelValueBeans;
	private List<LabelValueBean<String>> rmaLabelValueBeans;
	private List<LabelValueBean<Integer>> tatLevelLabelValueBeans;
	private List<Product> products;
	private List<Product> _4Slts;
	private List<LabelValueBean<Integer>> cidTypeLabelValueBeans;
	private List<LabelValueBean<Integer>> outletLabelValueBeans;
	private List<LabelValueBean<Integer>> customerFaultDescLabelValueBeans;

	private Integer customerId;
	private Order order;
	private Sn sn;
	private String pn;
	private String oldSn;
	private Product product;
	private String nextStep;
	
	@Wire
	private Window editWin;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		customerLabelValueBeans = labelValueBeanService.buildValidCustomersWithSelect();
		tatLevelLabelValueBeans = labelValueBeanService.buildByDictTypeId(Constants.TYPE_ID_TAT_LEVEL);
		cidTypeLabelValueBeans = labelValueBeanService.buildByDictTypeIdWithSelect(Constants.TYPE_ID_CID_TYPE);
		outletLabelValueBeans = labelValueBeanService.buildByDictTypeIdWithSelect(Constants.TYPE_ID_OUTLET);
		customerFaultDescLabelValueBeans = labelValueBeanService.buildByDictTypeIdWithSelect(Constants.TYPE_ID_CUSTOMER_FAULT_DESC);
		products = productService.queryAllSortedProducts();
		_4Slts = products;
		
		sn = getArgValue(Sn.class, KeyinQueryVM.KEY_SN);
		if (sn == null) {
			initForCustomerSelected(null);
			
		} else {
			order = orderService.queryById(Order.class, sn.getOrderId());
			rmaLabelValueBeans = labelValueBeanService.buildOpenRmasByCustomerIdWithSelect(order.getCustomerId());
			product = productService.queryById(Product.class, sn.getProductId());
			pn = product.getPn();

			customerId = order.getCustomerId();
//			sn.setPn(product.getPn());
			
			if (sn.getStatus().equals(SnStatus.WAIT_MIDH)) {
				nextStep = NEXT_STEP_MIDH;
			} else if (sn.getStatus().equals(SnStatus.WAIT_L1KEYIN)) {
				nextStep = NEXT_STEP_L1KEYIN;
			}
			
			oldSn = sn.getSn();
		}
	}
	
	private void initForCustomerSelected(Integer customerId) {
		sn = new Sn();
		order = new Order();
		rmaLabelValueBeans = labelValueBeanService.buildOpenRmasByCustomerIdWithSelect(customerId == null ? -1 : customerId);
		product = new Product();
	}
	
	@Command
	@NotifyChange({"rmaLabelValueBeans", "order"})
	public void onSelectCustomer() {
		initForCustomerSelected(customerId);
	}
	
	@Command
	@NotifyChange("order")
	public void onSelectRma() {
		if (!StringUtils.isBlank(order.getRma())) {
			order = orderService.queryOrderByRma(order.getRma());
		} else {
			initForCustomerSelected(customerId);
		}
	}
	
	@Command
	@NotifyChange("order")
	public void onSelectTatLevel() {
		String days = dictTypeCodeService.queryById(DictCode.class, order.getTatLevel()).getCodeName();
		order.setCloseTime(DateUtils.addDays(order.getReceiveTime(), Integer.parseInt(days)));
		
		orderService.doModifyOrder(order);
	}
	
	@Command
	@NotifyChange("order")
	public void onChangeTotalQty() {
		if (order.getTotalFinished() == null) {
			order.setTotalFinished(0);
			order.setTotalRemain(order.getTotalQty());
		} else if (order.getTotalQty() < order.getTotalFinished()) {
			showWarningBox(PropertiesUtils.getValueInSystem("keyin.edit.validate"));
		} else {
			order.setTotalRemain(order.getTotalQty() - order.getTotalFinished());
		}
		
		orderService.doModifyOrder(order);
	}
	
	/**
	 * <b>Function: <b>展示匹配结果
	 *
	 */
	@Command
	@NotifyChange("_4Slts")
	public void onListPns(@BindingParam("_4Pre")String _4Pre) {
		_4Slts = new ArrayList<Product>();
		
		for (Product vo : products) {
			if (vo.getPn().toUpperCase().contains(_4Pre.toUpperCase())) {
				_4Slts.add(vo);
			}
		}
	}
	
	@Command
	@NotifyChange({"sn", "product", "nextStep"})
	public void onSlt(@BindingParam("sltLb")Listbox sltLb, @BindingParam("bb")Bandbox bb) {
		if (sltLb.getSelectedItem() == null) {
			sn.setProductId(null);
		} else {
			sn.setProductId(((Product) sltLb.getSelectedItem().getValue()).getProductId());
		}
		
		bb.close();
		
		onFreshPn(sn.getProductId());
	}	

	@Command
	@NotifyChange({"sn", "product", "nextStep"})
	public void onClickPn(@BindingParam("product")Product product, @BindingParam("bb")Bandbox bb) {
		sn.setProductId(product.getProductId());
		
//		bb.setValue(product.getPn());
		bb.invalidate();
		bb.close();
		
		onFreshPn(product.getProductId());
	}
	
	@Command
	@NotifyChange({"product", "nextStep"})
	public void onFreshPn(@BindingParam("productId")Integer productId) {
		if (productId == null) {
			product = new Product();
			nextStep = null;
			
			return;
		}
		
		product = productService.queryById(Product.class, productId);
		
		// 自动设置“下步流程”
		DictCode dictCode = dictTypeCodeService.queryById(DictCode.class, product.getProductType());
		if (IF.YES.name().equals(dictCode.getCodeDesc())) {
			nextStep = NEXT_STEP_MIDH;
		} else {
			nextStep = NEXT_STEP_L1KEYIN;
		}
	}
	
	@Command
	@NotifyChange("sn")
	public void onFreshSn(@BindingParam("sn")String sn) {
		if (StringUtils.isBlank(sn) || this.sn.getSnId() != null) {
			return;
		}
		
		int times = snService.queryTwiceBackTimesBySn(sn).intValue();
		this.sn.setTwiceBackTimes(times);
		if (times == 0) {
			this.sn.setKeepStatus(KeepStatus.NEW);
		} else {
			this.sn.setKeepStatus(snService.querySnIsKeeping(sn) ? KeepStatus.WITHIN : KeepStatus.WITHOUT);
		}
	}
	
	@Command
	@NotifyChange({"order", "sn", "product", "nextStep"})
	public void onSubmit() throws Exception {
//		sn.setProductId(productService.queryProductByPn(sn.getPn()).getProductId());
		sn.setOrderId(order.getOrderId());
		
		if (sn.getProductId() == null) {
			showWarningBox("Please select PN");
			return;
		}
		if (StringUtils.isBlank(sn.getSn())) {
			showWarningBox("Please input SN");
			return;
		}
		if (sn.getSnId() == null
				|| (sn.getSnId() != null && !sn.getSn().equals(oldSn))){
			// 同一订单，不能出现相同 sn
			if (snService.isOrderSnExisting(order.getOrderId(), sn.getSn())) {
				showWarningBox("SN is existing in the same RMA. Please input another.");
				return;
			}
			// 其他订单中存在相同 sn ，询问是否继续
			if (snService.isSnExisting(sn.getSn())) {
				if (!showQuestionBox("SN is existing. Confirm to keep it also ?")) {
					return;
				}
			}
		}
		if (StringUtils.isBlank(nextStep)) {
			showWarningBox("Please select Next Step");
			return;
		}

		if (NEXT_STEP_MIDH.equals(nextStep)) {
			sn.setStatus(SnStatus.WAIT_MIDH);
		} else {
			sn.setStatus(SnStatus.WAIT_L1KEYIN);
		}
		
		if (sn.getSnId() == null) {
			snService.doCreateSn(sn, order);
		} else {
			snService.doModifySn(sn);
		}
		
		if (order.getTotalRemain() == 0) {
			showInformationBox(Constants.OPERATION_COMPLETED);
		} else {
			if (showQuestionBox(PropertiesUtils.getValueInSystem("keyin.edit.done.ask"))) {
				sn = new Sn();
				sn.setProductId(product.getProductId());
				
				// 保留上一PN
//				product = new Product();
//				nextStep = null;
			}
		}
		
//		Executions.getCurrent().sendRedirect(KeyinPrintVM.URL_KEYIN_PRINT + printSnId, "_blank");
//		snService.doPrint(printSnId);
	}
	
	@Command
	public void onFinish() throws Exception {
		if (!showQuestionBox(PropertiesUtils.getValueInSystem("keyin.edit.confirm"))) {
			return;
		}
		
		orderService.doFinishKeyin(order);
		
		showInformationBox(Constants.OPERATION_COMPLETED);
		editWin.detach();
	}
	
	public List<LabelValueBean<Integer>> getCustomerLabelValueBeans() {
		return customerLabelValueBeans;
	}

	public List<LabelValueBean<String>> getRmaLabelValueBeans() {
		return rmaLabelValueBeans;
	}

	public List<LabelValueBean<Integer>> getTatLevelLabelValueBeans() {
		return tatLevelLabelValueBeans;
	}

	public List<Product> get_4Slts() {
		return _4Slts;
	}

	public List<LabelValueBean<Integer>> getCidTypeLabelValueBeans() {
		return cidTypeLabelValueBeans;
	}

	public List<LabelValueBean<Integer>> getOutletLabelValueBeans() {
		return outletLabelValueBeans;
	}

	public List<LabelValueBean<Integer>> getCustomerFaultDescLabelValueBeans() {
		return customerFaultDescLabelValueBeans;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Sn getSn() {
		return sn;
	}

	public void setSn(Sn sn) {
		this.sn = sn;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public Product getProduct() {
		return product;
	}

	public String getNextStep() {
		return nextStep;
	}

	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}
	
}
