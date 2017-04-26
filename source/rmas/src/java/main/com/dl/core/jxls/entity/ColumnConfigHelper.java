package com.dl.core.jxls.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.ColumnConfigSupport;
import com.dl.core.jxls.model.ReportConfigSupport;
import com.dl.core.jxls.model.ValueMapper;
import com.dl.core.jxls.util.SpringBeanUtil;
import com.dl.core.jxls.validation.converter.ColumnConverter;

/**
 * ColumnConfig实体的辅助类
 * 
 * @author dylan
 * @date 2013-5-13 上午11:09:03
 */
@Component("upload.columnConfigHelper")
public class ColumnConfigHelper {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static Log logger = LogFactory.getLog(ColumnConfigHelper.class);

	/**
	 * 从配置信息中提取数据处理器和数据校验器
	 * 
	 * @return
	 */
	public List<Object> getProcessorAndValidators(ColumnConfig columnConfig) {
		if (columnConfig.processorAndValidators == null) {
			columnConfig.processorAndValidators = extractProcessorAndValidator(columnConfig);
		}
		return columnConfig.processorAndValidators;
	}

	private List<Object> extractProcessorAndValidator(ColumnConfig columnConfig) {
		String processorAndValidator = columnConfig.getProcessorAndValidator();
		List<Object> list = new ArrayList<Object>();
		if (StringUtils.isNotBlank(processorAndValidator)) {
			String[] vArr = processorAndValidator.split(";");
			for (String v : vArr) {
				Object bean = SpringBeanUtil.findBean(v);
				if (bean != null) {
					logger.debug("解析校验器\"" + v + "\"结果正确，加入校验器列表中.");
					// ignore null result
					if (bean instanceof ReportConfigSupport) {
						((ReportConfigSupport) bean)
								.setReportConfig(columnConfig.getReportConfig());
					}
					if (bean instanceof ColumnConfigSupport) {
						((ColumnConfigSupport) bean)
								.setColumnConfig(columnConfig);
					}
					list.add(bean);
				} else {
					logger.warn("解析校验器\"" + v + "\"结果为空，忽略.");
				}
			}
		}
		return list;
	}

	/**
	 * 抽取columnConfig中的数据转换器
	 * 
	 * @param columnConfig
	 * @return
	 */
	public ColumnConverter getColumnConverterBean(ColumnConfig columnConfig) {
		if (columnConfig.columnConverterBean == null) {
			columnConfig.columnConverterBean = extractConverterBean(columnConfig);
		}
		return columnConfig.columnConverterBean;
	}

	private ColumnConverter extractConverterBean(ColumnConfig columnConfig) {
		String columnConverter = columnConfig.getColumnConverter();
		if (StringUtils.isNotBlank(columnConverter)) {
			columnConverter = columnConverter.trim();
			Object bean = SpringBeanUtil.findBean(columnConverter);
			if (bean != null && bean instanceof ColumnConverter) {
				return (ColumnConverter) bean;
			}
		}
		return null;
	}

	/**
	 * 从配置信息中提取数据处理器和数据校验器
	 * 
	 * @return
	 */
	public List<ValueMapper> getValueMappers(ColumnConfig columnConfig) {
		if (columnConfig.valueMappers == null) {
			columnConfig.valueMappers = extractValueMappers(columnConfig);
		}
		return columnConfig.valueMappers;
	}

	private List<ValueMapper> extractValueMappers(ColumnConfig columnConfig) {
		String valueMapper = columnConfig.getValueMapper();
		List<ValueMapper> list = new ArrayList<ValueMapper>();
		if (StringUtils.isNotBlank(valueMapper)) {
			if (isSQLCaluse(valueMapper)) {

				SqlRowSet rst = jdbcTemplate.queryForRowSet(valueMapper);
				int columnCount = rst.getMetaData().getColumnCount();
				while (rst.next()) {
					if (columnCount > 1) {
						list.add(new ValueMapper(rst.getString(1), rst
								.getString(2)));
					} else {
						list.add(new ValueMapper(rst.getString(1)));
					}
				}
			} else {
				String[] vArr = valueMapper.split(";");
				for (String v : vArr) {
					int eIndex = v.indexOf("=");
					if (eIndex != -1) {
						list.add(new ValueMapper(v.substring(0, eIndex).trim(),
								v.substring(eIndex + 1).trim()));
					} else {
						list.add(new ValueMapper(v.trim()));
					}
				}
			}
		}
		return list;
	}

	private boolean isSQLCaluse(String valueMapper) {
		return valueMapper.trim().toLowerCase().startsWith("select ");
	}

}
