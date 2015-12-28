package com.dl.core.jxls.export.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas
 * @date 2014-12-12 下午2:03:25
 */
public class ExtraCellSupport {

	private boolean hasExtra = false;
	
	private List<ExtraCellDataModel> models = new ArrayList<ExtraCellDataModel>();
	
	public boolean existExtraData(){
		return hasExtra;
	}
	
	public void addCellModel(ExtraCellDataModel model){
		models.add(model);
		if (!hasExtra) hasExtra = true;
	}
	
	public void addAllCellModel(List<ExtraCellDataModel> ms){
		models.addAll(ms);
		if (!hasExtra) hasExtra = true;
	}

	public List<ExtraCellDataModel> getModels() {
		return models;
	}
	
}
