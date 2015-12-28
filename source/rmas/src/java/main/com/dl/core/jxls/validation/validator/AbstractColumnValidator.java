package com.dl.core.jxls.validation.validator;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.common.model.ParameterSet;
import com.dl.core.jxls.entity.ColumnConfig;
import com.dl.core.jxls.entity.ReportConfig;
import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.ColumnConfigSupport;
import com.dl.core.jxls.model.ReportConfigSupport;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.validation.ValidateResult;
import com.dl.core.jxls.validation.util.ValidateResourceBundle;

/**
 * 
 * @author dylan
 * @date 2012-8-21 下午2:10:55
 */
@Component
public abstract class AbstractColumnValidator implements
		ParameterizedColumnValidator, ReportConfigSupport, ColumnConfigSupport {
	protected final Log logger = LogFactory.getLog(getClass());
	/**
	 * 数据错误信息的key所使用的参数名称,如#nonNullVal?errMsg=nonNull
	 */
	public static final String PARAM_ERROR_MESSAGE = "errMsg";

	@Autowired
	protected ValidateResourceBundle validateResourceBundle;
	/**
	 * 配置的参数信息
	 */
	protected ParameterSet parameters;
	/**
	 * 当前列的列配置信息
	 */
	protected ColumnConfig columnConfig;
	/**
	 * 当前补贴项目的配置信息
	 */
	protected ReportConfig reportConfig;

	@Override
	public ParameterSet parameters() {
		return parameters;
	}

	@Override
	public void setParameters(ParameterSet parameters) {
		this.parameters = parameters;
	}

	/**
	 * 获取参数值
	 * 
	 * @param key
	 * @return
	 */
	public String getParameter(String key) {
		return getParameter(key, null);
	}

	/**
	 * 获取参数值,如果结果为null,使用默认结果
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getParameter(String key, String defaultValue) {
		String value = null;
		if (parameters == null
				|| (value = parameters.getParameter(key)) == null) {
			value = defaultValue;
		}
		return value;
	}

	/**
	 * 获取错误信息
	 * 
	 * @param defaultMessage
	 *            如果错误信息未定义或未配置时使用的默认信息
	 * @param args
	 *            可选的参数值
	 * @return
	 */
	public String getErrorMessage(String defaultMessage, Object... args) {
		if (validateResourceBundle == null) {
			return resolveMessage(defaultMessage, args);
		}
		String key = getParameter(PARAM_ERROR_MESSAGE);
		String msg = null;
		if (key == null) {
			key = getDefaultErrMsgKey();
		}
		if (key != null) {
			msg = validateResourceBundle.getMessage(key, args, defaultMessage);
		}
		if (StringUtils.isBlank(msg)) {
			return resolveMessage(defaultMessage, args);
		}
		return msg;
	}

	private String resolveMessage(String message, Object... args) {
		return args == null ? message : MessageFormat.format(message, args);
	}

	public ValidateResult validate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		if (StringUtils.isBlank(value) && ignoreBlankValue()) {
			return new ValidateResult();
		}
		boolean valid = doValidate(model, row, cell, value);
		if (!valid) {
			String errMsg = getErrorMessage(getDefaultErrMsg(), getErrParams());
			return new ValidateResult(false, errMsg);
		} else {
			return new ValidateResult();
		}
	}

	/**
	 * 进行数据的校验,并返回数据校验结果
	 * 
	 * @param model
	 * @param row
	 * @param cell
	 * @param value
	 * @return
	 */
	protected abstract boolean doValidate(UploadDataModel model,
			RowDataModel row, CellDataModel cell, String value);

	/**
	 * 如果未配置errMsg参数的情况下，使用的获取错误信息的key值,默认使用类名称做为key值
	 * 
	 * @return
	 */
	protected String getDefaultErrMsgKey() {
		return this.getClass().getSimpleName();
	}

	/**
	 * 默认的校验错误时的提示信息
	 * 
	 * @return
	 */
	protected String getDefaultErrMsg() {
		return "值校验有误!";
	}

	/**
	 * 对提示信息进行格式化时的参数列表
	 * 
	 * @return
	 */
	protected Object[] getErrParams() {
		return null;
	}

	/**
	 * 在数据校验时，是否忽略到空白字符串，如果结果为true(默认值),并且当前的值为null或空白字符串，则认为校验通
	 * 
	 * @return
	 */
	protected boolean ignoreBlankValue() {
		return true;
	}

	public ColumnConfig getColumnConfig() {
		return columnConfig;
	}

	@Override
	public void setColumnConfig(ColumnConfig columnConfig) {
		this.columnConfig = columnConfig;
	}

	@Override
	public ReportConfig getReportConfig() {
		return reportConfig;
	}

	@Override
	public void setReportConfig(ReportConfig tableDefine) {
		this.reportConfig = tableDefine;
	}

	/**
	 * 获取当前列的列别名
	 * 
	 * @return
	 */
	protected String getColumnAlias() {
		return getColumnAlias(getColumnConfig());
	}

	/**
	 * 获取列别名
	 * 
	 * @param columnName
	 * @return
	 */
	protected String getColumnAlias(String columnName) {
		for (ColumnConfig columnConfig : reportConfig.getColumnConfigs()) {
			if (columnName.equals(columnConfig.getColumnName())) {
				return getColumnAlias(columnConfig);
			}
		}
		return columnName;
	}

	private String getColumnAlias(ColumnConfig column) {
		String aliasName = column.getAliasName();
		return StringUtils.isNotBlank(aliasName) ? aliasName : column
				.getColumnName();
	}
}
