package com.dl.rmas.web.vm.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Label;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import com.dl.rmas.common.enums.IF;
import com.dl.rmas.common.utils.SecurityUtils;
import com.dl.rmas.entity.Menu;
import com.dl.rmas.service.MenuService;
import com.dl.rmas.web.zkmodel.MenuNode;

public class IndexVM extends BaseVM {
	
	public static final String URL_INDEX = "/zul/system/index.zul";

	@WireVariable
	private MenuService menuService;
	
	private MenuTreeRenderer menuRenderer;
	private DefaultTreeModel<Menu> menuModel;
	private String centerUrl;
	
	@Init
	public void init() {
		initTree();
		
		centerUrl = "/zul/statis/order_track.zul";
	}
	
	@Command
	public void onShowUserDetail() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(UserQueryVM.KEY_USER, SecurityUtils.getCurrentUser());
		
		showModal(UserDetailVM.URL_USER_DETAIL, args);
	}
	
	@Command
	public void onLogout() {
		SecurityUtils.removeCurrentUser();
		
		Executions.getCurrent().sendRedirect(UserLoginVM.URL_USER_LOGIN);
	}
	
	@Command
	@NotifyChange("centerUrl")
	public void onSelectMenu(@BindingParam("menuUrl")String menuUrl) {
		if (StringUtils.isNotBlank(menuUrl)) {
			centerUrl = menuUrl;
		}
	}
	
	private void initTree() {
		MenuNode root = new MenuNode(new Menu(), new ArrayList<DefaultTreeNode<Menu>>());
		List<Menu> menuList = menuService.queryMenusByUserId(SecurityUtils.getCurrentUserId());
		menuModel = new DefaultTreeModel<Menu>(buildTreeNode(root, menuList));

		menuRenderer = new MenuTreeRenderer();
	}
	
	private MenuNode buildTreeNode(MenuNode parentNode, List<Menu> menuList) {
	    List<TreeNode<Menu>> listChild = parentNode.getChildren();								
	    Menu parent = (Menu) (parentNode.getData());		
	    
	    for (Menu child : menuList) {								
	    	if (child.isUsed()) {
				continue;
			}
	    	
	        if (parent.getMenuId() == null || parent.getMenuId().equals(child.getParentId())) {
	        	MenuNode childNode;
	        	if (IF.NO.equals(child.getLeaf())) {
	        		childNode = new MenuNode(child, new ArrayList<DefaultTreeNode<Menu>>());
		            buildTreeNode(childNode, menuList);
				} else {
					childNode = new MenuNode(child);
				}
	            
	            listChild.add(childNode);
	            child.setUsed(true);
	        }					
	    }				
	    
	    return parentNode;
	}

	private final class MenuTreeRenderer implements TreeitemRenderer<MenuNode> {
		@Override
		public void render(Treeitem item, MenuNode menuNode, int index) throws Exception {
			Menu menu = menuNode.getData();
			
			Treerow row = item.getTreerow();
			if (row == null) {
				row = new Treerow();
				row.setParent(item);
			} else{
				row.getChildren().clear();
			}
			row.setHeight("32px");
			
			Treecell cell = new Treecell();
			Label label = new Label(menu.getMenuName());
			String labelStyle = "line-height:20px;";
			if (menu.getParentId() == null) {
				// 不打开“系统管理”菜单 deleted by dongbz 2014.12.13
//				if (!menu.getMenuId().equals(Constants.MENU_ID_SYSTEM_MANAGEMENT)) {
//					item.setOpen(true);
//				}
				item.setOpen(true);
				row.setStyle("background-color:#fbfbfb;");
				cell.setStyle("border-bottom:1px solid #e6edef;vertical-align:middle;");
				labelStyle = labelStyle + "font-weight:bold;";
			} else {
				cell.setStyle("border-bottom:1px solid #dfedf0;vertical-align:middle;");
			}
			label.setStyle(labelStyle);
			cell.appendChild(label);
			
			row.appendChild(cell);
			item.setValue(menu.getMenuUrl() != null ? menu.getMenuUrl() : "");
		}
	}
	
	public MenuTreeRenderer getMenuRenderer() {
		return menuRenderer;
	}

	public DefaultTreeModel<Menu> getMenuModel() {
		return menuModel;
	}

	public String getCenterUrl() {
		return centerUrl;
	}

	public void setCenterUrl(String centerUrl) {
		this.centerUrl = centerUrl;
	}

}
