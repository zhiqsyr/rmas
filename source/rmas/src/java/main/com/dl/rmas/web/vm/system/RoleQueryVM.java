package com.dl.rmas.web.vm.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.common.enums.IF;
import com.dl.rmas.entity.Menu;
import com.dl.rmas.entity.Role;
import com.dl.rmas.entity.RoleMenu;
import com.dl.rmas.service.MenuService;
import com.dl.rmas.service.RoleMenuService;
import com.dl.rmas.service.RoleService;
import com.dl.rmas.web.zkmodel.MenuNode;

public class RoleQueryVM extends BaseVM {

	public static final String KEY_ROLE = "role";
	
	@WireVariable
	private MenuService menuService;
	@WireVariable
	private RoleMenuService roleMenuService;
	@WireVariable
	private RoleService roleService;
	
	private List<Role> result;
	private Role selected;
	private List<RoleMenu> relatedMenus;
	
	private List<Menu> allValidMenus;
	private MenuTreeRenderer menuRenderer;
	private DefaultTreeModel<Menu> menuModel;
	
	@Init
	public void init() {
		onSearch();
		
		allValidMenus = menuService.queryAllValid();
	}
	
	private void onSearch() {
		result = roleService.queryAllValid(Role.class);
	}
	
	@Command
	@NotifyChange({"result", "selected"})
	public void onShowAdd() {
		showModal(RoleEditVM.URL_ROLE_EDIT);
		
		onSearch();
		selected = null;
	}
	
	@Command
	@NotifyChange({"result", "selected"})
	public void onShowEdit() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(KEY_ROLE, selected);
		showModal(RoleEditVM.URL_ROLE_EDIT, args);
	}
	
	@Command
	@NotifyChange({"result", "selected"})
	public void onDelete() {
		if (!showQuestionBox(Constants.CONFIRM_TO_DELETE)) {
			return;
		}
		
		roleService.doDeleteRole(selected);
		onSearch();
		selected = null;
	}
	
	@Command
	@NotifyChange({"menuModel", "menuRenderer"})
	public void onSelectRole() {
		relatedMenus = roleMenuService.queryRoleMenusByRoleId(selected.getRoleId());
		initTree();
	}
	
	@Command
	public void onSaveRoleMenus(@BindingParam("menuTree")Tree menuTree) {
		List<Menu> selectedMenus = new ArrayList<Menu>();
		Checkbox cb;
		for (Menu menu : allValidMenus) {
			// 一级菜单不作关联
			if (menu.getParentId() == null) {
				continue;
			}
			
			cb = (Checkbox) menuTree.getFellow("menu_" + menu.getMenuId().toString());
			if (cb.isChecked()) {
				selectedMenus.add(menu);
			}
		}
		
		roleMenuService.doCreateRoleMenus(selected, selectedMenus);
		showInformationBox(Constants.OPERATION_COMPLETED);
	}
	
	private void initTree() {
		MenuNode root = new MenuNode(new Menu(), new ArrayList<DefaultTreeNode<Menu>>());
		if (menuModel == null) {
			menuModel = new DefaultTreeModel<Menu>(buildTreeNode(root, allValidMenus));
		}

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
			
			Treecell cell = new Treecell();
			Label label = new Label(menu.getMenuName());
			if (menu.getParentId() == null) {
				item.setOpen(true);
				label.setStyle("font-weight:bold;");
			} else {
				Checkbox cb = new Checkbox();
				if (isMenuRelated(menu.getMenuId())) {
					cb.setChecked(true);
				}
				cb.setId("menu_" + menu.getMenuId().toString());
				cell.appendChild(cb);
				label.setStyle("margin-left:5px");
			}
			cell.appendChild(label);
			
			row.appendChild(cell);
			item.setValue(menu.getMenuId());
		}
	}
	
	private boolean isMenuRelated(Integer menuId) {
		for (RoleMenu rm : relatedMenus) {
			if (menuId.equals(rm.getMenuId())) {
				return true;
			}
		}
		
		return false;
	}
	
	public List<Role> getResult() {
		return result;
	}

	public Role getSelected() {
		return selected;
	}

	public void setSelected(Role selected) {
		this.selected = selected;
	}
	
	public MenuTreeRenderer getMenuRenderer() {
		return menuRenderer;
	}

	public DefaultTreeModel<Menu> getMenuModel() {
		return menuModel;
	}
	
}
