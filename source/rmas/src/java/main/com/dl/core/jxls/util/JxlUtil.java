package com.dl.core.jxls.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.CellFeatures;
import jxl.CellType;
import jxl.CellView;
import jxl.DateCell;
import jxl.Range;
import jxl.Sheet;
import jxl.format.Alignment;
import jxl.format.BoldStyle;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.Font;
import jxl.format.RGB;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;

import com.dl.core.jxls.parse.model.CellModel;
import com.dl.core.jxls.parse.model.CellModel.AlignType;
import com.dl.core.jxls.parse.model.CellModel.BorderStyle;
import com.dl.core.jxls.parse.model.CellModel.BorderWidth;
import com.dl.core.jxls.parse.model.CellModel.VAlignType;
import com.dl.core.jxls.parse.model.MergedCell;

/**
 * Excel数据解析工具类，使用JXL对excel文件进行解析
 * 
 * @author dylan
 * @date 2013-5-8 下午2:20:15
 */
public class JxlUtil {
	public static final Pattern PATTERN_COL_NAME = Pattern
			.compile("^([A-Z]+)$");
	public static final Pattern PATTERN_CELL_NAME = Pattern
			.compile("^([A-Z]+)([0-9]+)$");

	private static final int A_VALUE = (int) 'A';

	/**
	 * 获取所有合并的单元格
	 * 
	 * @param sheet
	 * @return
	 */
	public static List<MergedCell> getMergedCells(Sheet sheet) {
		List<MergedCell> list = new ArrayList<MergedCell>();
		Range[] ranges = sheet.getMergedCells();
		if (ranges == null) {
			return list;
		}
		for (Range range : ranges) {
			Cell topLeft = range.getTopLeft();
			Cell bottomRight = range.getBottomRight();
			MergedCell mergedCell = new MergedCell(topLeft.getRow(),
					topLeft.getColumn(), bottomRight.getRow(),
					bottomRight.getColumn(), topLeft);
			list.add(mergedCell);
		}
		return list;
	}

	/**
	 * 获取行高
	 * 
	 * @param row
	 * @param sheet
	 * @return
	 */
	public static int getRowHeight(int row, Sheet sheet) {
		CellView rowView = sheet.getRowView(row);
		return rowView != null ? rowView.getSize() : 0;
	}
	
	/**
	 * 获取列宽
	 * 
	 * @param col
	 * @param sheet
	 * @return
	 */
	public static int getColumnWidth(int col, Sheet sheet) {
		CellView cellView = sheet.getColumnView(col);
		return cellView != null ? cellView.getSize() : 0;
	}

	/**
	 * 从合并单元格列表中找出指定行的合并单元格
	 * 
	 * @param row
	 * @param mergedCells
	 * @return
	 */
	public static List<MergedCell> extractRowMergedCells(int row,
			List<MergedCell> mergedCells) {
		List<MergedCell> list = new ArrayList<MergedCell>();
		if (mergedCells == null) {
			return list;
		}
		for (MergedCell mergedCell : mergedCells) {
			if (row == mergedCell.getStartRow()) {
				list.add(mergedCell);
			}
		}
		return list;
	}

	/**
	 * 从合并单元格列表中找出指定列的合并单元格
	 * 
	 * @param col
	 * @param mergedCells
	 * @return
	 */
	public static List<MergedCell> extractColMergedCells(int col,
			List<MergedCell> mergedCells) {
		List<MergedCell> list = new ArrayList<MergedCell>();
		if (mergedCells == null) {
			return list;
		}
		for (MergedCell mergedCell : mergedCells) {
			if (col == mergedCell.getStartCol()) {
				list.add(mergedCell);
			}
		}
		return list;
	}

	/**
	 * 判断单元格是否位于合并的单元格中
	 * 
	 * @param cell
	 * @param mergedCells
	 * @return
	 */
	public static boolean isMergedCell(Cell cell, List<MergedCell> mergedCells) {
		if (mergedCells == null) {
			return false;
		}
		int row = cell.getRow();
		int col = cell.getColumn();
		for (MergedCell range : mergedCells) {
			if (range.getStartRow() <= row && range.getEndRow() >= row
					&& range.getStartCol() <= col && range.getEndCol() >= col) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取单元格中的内容
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellContent(Cell cell) {
		if (cell == null) {
			return null;
		}
		return cell.getContents();
	}

	/**
	 * 获取单元格的标注信息
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellComment(Cell cell) {
		if (cell == null) {
			return null;
		}
		CellFeatures cellFeatures = cell.getCellFeatures();
		if (cellFeatures != null) {
			return cellFeatures.getComment();
		}
		return null;
	}

	

	/**
	 * 根据cell的样式定义，为td构造样式相关的信息
	 * 
	 * @param cell
	 * @param td
	 */
	public static void buildTDStyle(Cell cell, CellModel td) {
		CellFormat format = cell.getCellFormat();
		if (format == null) {
			return;
		}
		// 横向对齐方式
		Alignment align = format.getAlignment();
		if (align != null) {
			if (align.getValue() == Alignment.CENTRE.getValue()) {
				td.setAlign(AlignType.center);
			} else if (align.getValue() == Alignment.RIGHT.getValue()) {
				td.setAlign(AlignType.right);
			} else {
				td.setAlign(AlignType.left);
			}
		}
		// 纵向对齐方式
		VerticalAlignment vAlign = format.getVerticalAlignment();
		if (vAlign != null) {
			if (vAlign.getValue() == VerticalAlignment.CENTRE.getValue()) {
				td.setVAlign(VAlignType.middle);
			} else if (vAlign.getValue() == VerticalAlignment.BOTTOM.getValue()) {
				td.setVAlign(VAlignType.bottom);
			} else {
				td.setVAlign(VAlignType.top);
			}
		}
		// 背景色
		Colour bgColor = format.getBackgroundColour();
		if (bgColor != null) {
			td.setBgColor(rgbToHex(bgColor.getDefaultRGB()));
		}
		// 字体
		Font font = format.getFont();
		if (font != null) {
			// 字体加重
			if (font.getBoldWeight() == BoldStyle.BOLD.getValue()) {
				td.setFontBold(true);
			}
			// 字体大小
			td.setFontSize(font.getPointSize());
			// 斜体
			if (font.isItalic()) {
				td.setFontItalic(true);
			}
			// 字体颜色
			Colour fontColor = font.getColour();
			if (fontColor != null) {
				td.setFontColor(rgbToHex(fontColor.getDefaultRGB()));
			}
			// 字体下划线
			UnderlineStyle ulStyle = font.getUnderlineStyle();
			if (ulStyle != null) {
				td.setFontUnderline(ulStyle.getDescription());
			}
		}
		// 边框
		if (format.hasBorders()) {
			td.setLeftBorder(extractBorder(format, Border.LEFT));
			td.setRightBorder(extractBorder(format, Border.RIGHT));
			td.setTopBorder(extractBorder(format, Border.TOP));
			td.setBottomBorder(extractBorder(format, Border.BOTTOM));
		}
	}

	private static CellModel.Border extractBorder(
			CellFormat format, Border cellBorder) {
		BorderLineStyle style = format.getBorder(cellBorder);
		if (style == null
				|| style.getValue() == BorderLineStyle.NONE.getValue()) {
			// 无边框和边框样式
			return null;
		}
		Colour color = format.getBorderColour(cellBorder);
		CellModel.Border border = new CellModel.Border();
		if (color != null) {
			border.setColor(rgbToHex(color.getDefaultRGB()));
		}
		if (style != null) {
			int s = style.getValue();
			if (s == BorderLineStyle.THIN.getValue()
					|| s == BorderLineStyle.HAIR.getValue()) {// thin or hair
				border.setWidth(BorderWidth.thin);
			} else if (s == BorderLineStyle.MEDIUM.getValue()) {// medium
				border.setWidth(BorderWidth.medium);
			} else if (s == BorderLineStyle.THICK.getValue()) {// thick
				border.setWidth(BorderWidth.thick);
			} else if (s == BorderLineStyle.DASHED.getValue()) {// dashed
				border.setStyle(BorderStyle.DASHED);
			} else if (s == BorderLineStyle.DOTTED.getValue()
					|| s == BorderLineStyle.DASH_DOT.getValue()
					|| s == BorderLineStyle.DASH_DOT_DOT.getValue()
					|| s == BorderLineStyle.SLANTED_DASH_DOT.getValue()) {// dotted
				border.setStyle(BorderStyle.DOTTED);
			} else if (s == BorderLineStyle.DOUBLE.getValue()) {// double
				border.setStyle(BorderStyle.DOUBLE);
			} else if (s == BorderLineStyle.MEDIUM_DASHED.getValue()) {// medium_dashed
				border.setWidth(BorderWidth.medium);
				border.setStyle(BorderStyle.DASHED);
			} else if (s == BorderLineStyle.MEDIUM_DASH_DOT.getValue()
					|| s == BorderLineStyle.MEDIUM_DASH_DOT_DOT.getValue()) {// MEDIUM_DASH_DOT
				border.setWidth(BorderWidth.medium);
				border.setStyle(BorderStyle.DOTTED);
			}
		}
		return border;
	}

	/**
	 * 单位换算，excel中的单位为磅，换算为像素,1磅=4/3px
	 * 
	 * @param size
	 * @return
	 */
	public static int sizeToPX(int size) {
		return (int) (size * 4 / 3);
	}

	/**
	 * 将RGB转换为16进制的表示形式
	 * 
	 * @param rgb
	 *            ,如RGB(0,0,0)->#000000, RGB(255,255,255)->#ffffff
	 * @return
	 */
	public static String rgbToHex(RGB rgb) {
		String r = Integer.toHexString(rgb.getRed());
		if (r.length() < 2) {
			r = "0" + r;
		}
		String g = Integer.toHexString(rgb.getGreen());
		if (g.length() < 2) {
			g = "0" + g;
		}
		String b = Integer.toHexString(rgb.getBlue());
		if (b.length() < 2) {
			b = "0" + b;
		}
		return "#" + r + g + b;
	}
	

	/**
	 * 由列名计算出该列的位置，如A->0,B->1,AA->26
	 * 
	 * @param colName
	 * @return
	 */
	public static int getCol(String colName) {
		colName = colName.trim().toUpperCase();
		if (!PATTERN_COL_NAME.matcher(colName).matches()) {
			throw new IllegalArgumentException("列引用[" + colName + "]格式不正确.");
		}
		int len = colName.length() - 1;
		char c = colName.charAt(len);
		int col = ((int) c - A_VALUE);
		for (int i = 1; i <= len; i++) {
			c = colName.charAt(len - i);
			col += ((int) c - A_VALUE + 1) * Math.pow(26, i);
		}
		return col;
	}

	/**
	 * 由列所在的位置得到列的名称,如0->A,1->B,26->AA
	 * 
	 * @param col
	 * @return
	 */
	public static String getColName(int col) {
		StringBuilder sb = new StringBuilder();
		if (col < 26) {
			sb.append((char) (A_VALUE + col));
		} else {
			sb.append(getColName(col / 26 - 1));
			sb.append((char) (A_VALUE + col % 26));
		}
		return sb.toString();
	}

	/**
	 * 获得单元格字符串形式的值
	 * 
	 * @param cell
	 * @return
	 */
	public static Object getValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		if(cell.getType() == CellType.DATE){
			return ((DateCell)cell).getDate();
		}
		return cell.getContents();
	}

	/**
	 * 检查字符串是否为单元格名称,如A3->true,B5->true,B->false,X->false
	 * @param cell
	 * @return
	 */
	public static boolean isCellName(String cell){
		return PATTERN_CELL_NAME.matcher(cell).matches();
	}
	
	/**
	 * 检查字符串是否为单元格列名,如A3->false,B5->false,B->true,X->true
	 * @param cell
	 * @return
	 */
	public static boolean isColName(String cell){
		return PATTERN_COL_NAME.matcher(cell).matches();
	}

}
