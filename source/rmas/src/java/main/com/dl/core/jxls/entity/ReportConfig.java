package com.dl.core.jxls.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "EXCEL_REPORT_CONFIG")
public class ReportConfig implements Serializable {

	private static final long serialVersionUID = 162810334755629834L;
	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "uuid")
	@GeneratedValue(generator = "uuidGenerator")
	@Column(name = "id")
	private String id;
	
	@Column(name = "report_name")
	private String reportName;
	
	@Column(name = "table_name")
	private String tableName;
	
	@Column(name = "start_row")
	private Integer startRow;
	
	@Column(name = "end_row")
	private Integer endRow;
	
	@Column(name = "start_col")
	private Integer startCol;
	
	@Column(name = "end_col")
	private Integer endCol;
	
	@Column(name = "serial_num_col")
	private String serialNumCol;

	// 模板文件版本号
	@Column(name = "template_version")
	private String templateVersion;
	// 模板文件存放的路径
	@Column(name = "template_path")
	private String templatePath;
	// 解析的sheet页
	@Column(name = "sheet_name")
	private String sheetName;
	@Column(name = "report_type")
	private String reportType;

	@OneToMany(mappedBy = "reportConfig")
	private List<ColumnConfig> columnConfigs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public Integer getStartCol() {
		return startCol;
	}

	public void setStartCol(Integer startCol) {
		this.startCol = startCol;
	}

	public Integer getEndCol() {
		return endCol;
	}

	public void setEndCol(Integer endCol) {
		this.endCol = endCol;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public String getSerialNumCol() {
		return serialNumCol;
	}

	public void setSerialNumCol(String serialNumCol) {
		this.serialNumCol = serialNumCol;
	}

	public String getTemplateVersion() {
		return templateVersion;
	}

	public void setTemplateVersion(String templateVersion) {
		this.templateVersion = templateVersion;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public List<ColumnConfig> getColumnConfigs() {
		return columnConfigs;
	}

	public void setColumnConfigs(List<ColumnConfig> columnConfigs) {
		this.columnConfigs = columnConfigs;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

}
