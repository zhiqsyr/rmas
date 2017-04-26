package com.dl.rmas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.entity.Bom;
import com.dl.rmas.entity.SnRepairMaterial;
import com.dl.rmas.service.BomService;
import com.dl.rmas.service.SnRepairMaterialService;

@Service("snRepairMaterialService")
public class SnRepairMaterialServiceImpl extends BaseServiceImpl implements SnRepairMaterialService {

	@Autowired
	private BomService bomService;
	
	@Override
	public List<SnRepairMaterial> queryBySnId(Integer snId) {
		SnRepairMaterial query = new SnRepairMaterial();
		query.setSnId(snId);
		
		List<SnRepairMaterial> result = queryByExample(query);
		Bom bom;
		for (SnRepairMaterial vo : result) {
			bom = bomService.queryById(Bom.class, vo.getBomId());
			vo.setBom(bom);
		}
		
		return result;
	}
	
	@Override
	public void doCreateOrModify(SnRepairMaterial snRepairMaterial) {
		if (snRepairMaterial.getRmId() == null) {
			snRepairMaterial.setCreateUser(currentUserId());
			snRepairMaterial.setCreateTime(currentTime());
			snRepairMaterial.setValidity(Validity.VALID);
			
			doSave(snRepairMaterial);
		} else {
			snRepairMaterial.setLastModifier(currentUserId());
			snRepairMaterial.setLastModifyTime(currentTime());
			
			doUpdate(snRepairMaterial);
		}
	}
	
}
