package com.dl.rmas.service;

import java.util.List;

import com.dl.rmas.entity.SnRepairMaterial;

public interface SnRepairMaterialService extends BaseService {

	List<SnRepairMaterial> queryBySnId(Integer snId);

	void doCreateOrModify(SnRepairMaterial snRepairMaterial);
	
}
