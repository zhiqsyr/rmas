package com.dl.core.jxls.validation.processor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;

/**
 * 
 * @author dylan
 * @date 2013-1-23 上午10:18:39
 */
@Component("batchIdPro")
@Scope("prototype")
public class UploadBatchIdProcessor extends AbstractColumnProcessor{

	@Override
	protected void doProcess(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		String batchId = model.properties().getParameter("batchId");
		cell.setValue(batchId);
	}

}
