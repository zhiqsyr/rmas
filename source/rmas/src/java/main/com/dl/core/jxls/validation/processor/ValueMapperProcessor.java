package com.dl.core.jxls.validation.processor;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.entity.ColumnConfigHelper;
import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.model.ValueMapper;

/**
 * 将值映射为valueMapper中定义的key
 * 
 * @author dylan
 * @date 2013-5-14 下午4:41:11
 */
@Component("valueMapperPro")
@Scope("prototype")
public class ValueMapperProcessor extends AbstractColumnProcessor {

	@Autowired
	ColumnConfigHelper configHelper;

	@Override
	protected void doProcess(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
			for (ValueMapper vm : configHelper.getValueMappers(columnConfig)) {
				if (value.equals(vm.getLabel())) {
					cell.setValue(vm.getValue());
					break;
				}
			}
		}
	}

}
