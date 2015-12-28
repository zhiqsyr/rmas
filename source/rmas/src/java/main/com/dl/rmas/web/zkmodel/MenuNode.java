package com.dl.rmas.web.zkmodel;

import java.util.List;

import org.zkoss.zul.DefaultTreeNode;

import com.dl.rmas.entity.Menu;

@SuppressWarnings("serial")
public class MenuNode extends DefaultTreeNode<Menu> {

	public MenuNode(Menu data) {
		super(data);
	}

	public MenuNode(Menu data, DefaultTreeNode<Menu>[] children) {
		super(data, children);
	}
	
	public MenuNode(Menu data, List<DefaultTreeNode<Menu>> children) {
		super(data, children);
	}
	
}
