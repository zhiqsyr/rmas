package com.dl.rmas.service;

import java.util.List;

import com.dl.rmas.entity.Bom;
import com.dl.rmas.web.zkmodel.PagingDto;

public interface BomService extends BaseService {

	List<Bom> queryByProductId(Integer productId);

	List<Bom> queryBomsByQueryDto(Bom queryBom, PagingDto pagingDto);

	void doDeleteAllByProductId(Integer productId);
	
}
