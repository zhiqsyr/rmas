package com.dl.rmas.service;

import java.util.List;

import com.dl.rmas.entity.DictCode;

public interface DictTypeCodeService extends BaseService {

	List<DictCode> queryDictCodesByDictTypeId(Integer dictTypeId);
	
	void doCreateOrModifyDictCode(DictCode dictCode);
	
	void doRemove(DictCode dictCode);
	
}
