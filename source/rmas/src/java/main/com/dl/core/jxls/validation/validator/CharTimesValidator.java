package com.dl.core.jxls.validation.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;

/**
 * @author Lucas
 * @date 2014-4-3 下午3:39:07
 */
@Component("charTimesVal")
@Scope("prototype")
public class CharTimesValidator extends AbstractColumnValidator {

	protected String PARAM_NAME_TIMES = "t";//最多出现的次数
	protected String PARAM_NAME_RESOURCE = "n";
	protected String[] NAME_CHAR = new String[]{"；", ";"};//出现的字符,无法配置到校验器有冲突
	private String errMsg = null;
	
	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		value = value.trim();
		String required = getParameter(PARAM_NAME_TIMES, null);
		String resource = getParameter(PARAM_NAME_RESOURCE, null);
		int times = StringUtils.isNotBlank(required) ? Integer.valueOf(required) : 0;
		int _timesValue = 0;
		
		if (StringUtils.isNotBlank(required)
				&& StringUtils.isNotBlank(value)){
			for (String c : NAME_CHAR) {
				_timesValue += judgeTimesFromString(value, c);
			}
		}
		
		if (_timesValue > times
				|| (_timesValue == times
				&& !value.endsWith(";")
				&& !value.endsWith("；"))){
			errMsg = getErrorMessage("最多填写{0}个{1}.", times, resource);
			return false;
		}
		
		return true;
	}

	//判断出现几次字符
	private int judgeTimesFromString(String str, String ch) {
		int _temp = 0;
		while (str.indexOf(ch) != -1) {
			str = str.replaceFirst(ch, "");
			_temp++;
		}
		return _temp;
	}
	
	@Override
	protected String getDefaultErrMsg() {
		return errMsg;
	}
	
}
