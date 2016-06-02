package com.dl.rmas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.entity.DictCode;
import com.dl.rmas.service.DictTypeCodeService;

@Service("dictTypeCodeService")
public class DictTypeCodeServiceImpl extends BaseServiceImpl implements DictTypeCodeService {

	@Override
	public List<DictCode> queryDictCodesByDictTypeId(Integer dictTypeId) {
		Assert.notNull(dictTypeId);
		
		DictCode dictCode = new DictCode();
		dictCode.setTypeId(dictTypeId);
		dictCode.setValidity(Validity.VALID);
		
		return queryByExample(dictCode);
	}
	
	@Override
	public void doCreateOrModifyDictCode(DictCode dictCode) {
		if (dictCode.getCodeId() == null) {
			dictCode.setCreateUser(currentUserId());
			dictCode.setCreateTime(currentTime());
			dictCode.setValidity(Validity.VALID);
			doSave(dictCode);
		} else {
			dictCode.setLastModifier(currentUserId());
			dictCode.setLastModifyTime(currentTime());
			doUpdate(dictCode);
		}
	}
	
	@Override
	public void doRemove(DictCode dictCode) {
		dictCode.setValidity(Validity.INVALID);
		
		doCreateOrModifyDictCode(dictCode);
	}
	
}
