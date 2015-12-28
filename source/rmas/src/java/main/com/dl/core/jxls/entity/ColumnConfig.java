package com.dl.core.jxls.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.dl.core.jxls.model.ValueMapper;
import com.dl.core.jxls.validation.converter.ColumnConverter;

@Entity
@Table(name = "EXCEL_COLUMN_CONFIG")
public class ColumnConfig implements Serializable, Comparable<ColumnConfig> {
	private static final long serialVersionUID = -3137094100706706628L;

	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "uuid")
	@GeneratedValue(generator = "uuidGenerator")
	private String id;

	@Column(name = "report_id")
	private String reportId;
	@Column(name = "column_name")
	private String columnName;
	@Column(name = "alias_name")
	private String aliasName;
	@Column(name = "data_position")
	private String dataPosition;
	@Column(name = "processor_and_validator")
	private String processorAndValidator;
	@Column(name = "script_validator")
	private String scriptValidator;
	// 对于select,checkbox,radio等类型的数据，配置其数据来源
	@Column(name = "value_mapper")
	private String valueMapper;
	@Column(name = "need_save")
	private Boolean needSave;
	@Column(name = "need_show")
	private Boolean needShow;
	@Column(name = "ordinal")
	private Integer ordinal;
	@Column(name = "data_type")
	private String dataType;
	@Column(name = "column_converter")
	private String columnConverter;
	@Column(name = "serial")
	private String serial;//合作社组织情况统计报表中的序号

	@ManyToOne
	@JoinColumn(name = "report_id", insertable = false, updatable = false)
	private ReportConfig reportConfig;

	@Transient
	List<Object> processorAndValidators;
	@Transient
	List<ValueMapper> valueMappers;
	@Transient
	ColumnConverter columnConverterBean;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getDataPosition() {
		return dataPosition;
	}

	public void setDataPosition(String dataPosition) {
		this.dataPosition = dataPosition;
	}

	public String getProcessorAndValidator() {
		return processorAndValidator;
	}

	public void setProcessorAndValidator(String processorAndValidator) {
		this.processorAndValidator = processorAndValidator;
	}

	public Boolean isNeedSave() {
		return needSave;
	}

	public void setNeedSave(Boolean needSave) {
		this.needSave = needSave;
	}

	public Boolean isNeedShow() {
		return needShow;
	}

	public void setNeedShow(Boolean needShow) {
		this.needShow = needShow;
	}

	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public ReportConfig getReportConfig() {
		return reportConfig;
	}

	public void setReportConfig(ReportConfig reportConfig) {
		this.reportConfig = reportConfig;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColumnConverter() {
		return columnConverter;
	}

	public void setColumnConverter(String columnConverter) {
		this.columnConverter = columnConverter;
	}

	public String getScriptValidator() {
		return scriptValidator;
	}

	public void setScriptValidator(String scriptValidator) {
		this.scriptValidator = scriptValidator;
	}

	public String getValueMapper() {
		return valueMapper;
	}

	public void setValueMapper(String valueMapper) {
		this.valueMapper = valueMapper;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	@Override
	public int compareTo(ColumnConfig other) {
		if (this.ordinal == null) {
			return 1;
		}
		if (other.ordinal == null) {
			return -1;
		}
		return this.ordinal < other.ordinal ? -1 : 1;
	}

}
