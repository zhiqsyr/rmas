package com.dl.core.jxls.parse;

import jxl.CellReferenceHelper;

import org.hibernate.util.StringHelper;

import com.dl.core.jxls.entity.ReportConfig;
import com.dl.core.jxls.model.ReportConfigSupport;
import com.dl.core.jxls.parse.model.RowModel;
import com.dl.core.jxls.util.JxlUtil;

/**
 * 通过reportConfig中配置的serialNumCol列数据是否为空来决定是否进行后续的内容解析
 * 
 * @author dylan
 * @date 2013-5-9 下午4:34:08
 */
public class ReportConfigSupportParser extends ExcelTemplateParser implements
		ReportConfigSupport {
	private ReportConfig reportConfig;

	public ReportConfig getReportConfig() {
		return reportConfig;
	}

	public void setReportConfig(ReportConfig reportConfig) {
		this.reportConfig = reportConfig;
	}

	@Override
	protected int beforeAddRow(RowModel tr) {
		String serailCol = reportConfig.getSerialNumCol();
		if (StringHelper.isEmpty(serailCol)) {
			return super.beforeAddRow(tr);
		} else {
			String content;
			if (JxlUtil.isColName(serailCol)) {
				// 列名
				content = tr.getCellContent(CellReferenceHelper
						.getColumn(serailCol));
			} else {
				int col = Integer.parseInt(serailCol.trim());
				content = tr.getCellContent(col - 1);
			}
			return StringHelper.isEmpty(content) ? SKIP_SHEET : PARSE_CONTINUE;
		}
	}

}
