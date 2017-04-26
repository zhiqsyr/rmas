package com.dl.core.jxls.validation.validator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.util.StringHelper;
import com.dl.core.jxls.validation.util.ValidateUtils;
/**
 * 
 * @author JIANGHUIJI
 * 2013-7-3 下午4:01:28
 */
@Component("onlyOneVal")
@Scope("prototype")
public class NonNullOnlyOneValidator extends AbstractColumnValidator {

	protected String PARAM_NAME_SOURCE = "s";
	protected String PARAM_PARNET_COL = "c";
	// TODO 是否将0当作null处理，对于1,0选择的输入框，把0当作null来处理
	
	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		int num=0;
		String parentParam = getParameter(PARAM_PARNET_COL, null);
		String param = getParameter(PARAM_NAME_SOURCE, null);
		if (StringHelper.isNotEmpty(param)) {
			for (String c : ValidateUtils.extractPositions(param)) {
				String val = row.getRowData().getCellContent(Integer.parseInt(c)-1);
				if (val != null && val!="" && !val.equals("　")) {
					num = num + 1;
				}
			}
		}
		
		if(num==1){
			if(parentParam!=null){
				String val = row.getRowData().getCellContent(Integer.parseInt(parentParam)-1);
				if (val != null && val!="" && !val.equals("　")) {
					return true;
				}else{
					return false;
				}
			}else{
				return true;
			}
		}else if(num==0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	protected String getDefaultErrMsg() {
		String param = getParameter(PARAM_NAME_SOURCE, null);
		String parentParam = getParameter(PARAM_PARNET_COL, null);
		StringBuffer sb = new StringBuffer();
		if (StringHelper.isNotEmpty(param)) {
			for (String c : ValidateUtils.extractPositions(param)) {
				sb.append(c).append(",");
			}
		}
		if(StringHelper.isNotEmpty(parentParam)){
			return "选择"+sb+"行时，"+parentParam+"行必选";
		}else{
			return sb+"行必选且只能选一个";
		}
	}
	
	@Override
	protected String getDefaultErrMsgKey() {
		return "onlyOne";
	}

	@Override
	protected boolean ignoreBlankValue() {
		return false;
	}
	


}
