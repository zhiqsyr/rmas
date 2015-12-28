package com.dl.core.jxls.parse.model;

/**
 * 单元格TD模型
 * @author dylan
 * @date 2013-5-6 上午9:15:23
 */
public class CellModel {
	private int serial;//列号(或行号)，以0作为起始列号(或行号)
	private int colspan = 1;
	private int rowspan = 1;
	private String content;
	private String comment;

	private String bgColor;
	private String fontColor;
	private int fontSize;
	private String fontUnderline;
	private boolean fontBold = false;
	private boolean fontItalic = false;
	private AlignType align = AlignType.left;
	private VAlignType vAlign = VAlignType.top;
	private Border leftBorder;
	private Border rightBorder;
	private Border topBorder;
	private Border bottomBorder;
	/**
	 * 横向对齐方式
	 */
	public static enum AlignType {
		left, center, right;
	}
	/**
	 * 纵向对齐方式
	 */
	public static enum VAlignType {
		top, middle, bottom;
	}
	/**
	 * 边框的宽度,css:border-with
	 */
	public static enum BorderWidth {
		thin,medium,thick
	}
	/**
	 * 边框的样式,css:border-style
	 */
	public static enum BorderStyle {
		NONE,
		/**
		 * 点状
		 */
		DOTTED,
		/**
		 * 虚线
		 */
		DASHED,
		/**
		 * 实线
		 */
		SOLID,
		/**
		 * 双线
		 */
		DOUBLE
	}
	/**
	 * 边框样式
	 * @author dylan
	 * @date 2013-5-9 上午10:22:23
	 */
	public static class Border {
		/**
		 * border-width:thin,medium,thick等
		 */
		private BorderWidth width = BorderWidth.thick;
		/**
		 * border-style,{@link BorderStyle}
		 */
		private BorderStyle style = BorderStyle.SOLID;
		/**
		 * border-color
		 */
		private String color;

		public Border() {
			
		}

		public Border(BorderWidth width, BorderStyle style, String color) {
			super();
			this.width = width;
			this.style = style;
			this.color = color;
		}

		public BorderWidth getWidth() {
			return width;
		}

		public void setWidth(BorderWidth width) {
			this.width = width;
		}

		public BorderStyle getStyle() {
			return style;
		}

		public void setStyle(BorderStyle style) {
			this.style = style;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

	}

	public Border getLeftBorder() {
		return leftBorder;
	}

	public void setLeftBorder(Border leftBorder) {
		this.leftBorder = leftBorder;
	}

	public Border getRightBorder() {
		return rightBorder;
	}

	public void setRightBorder(Border rightBorder) {
		this.rightBorder = rightBorder;
	}

	public Border getTopBorder() {
		return topBorder;
	}

	public void setTopBorder(Border topBorder) {
		this.topBorder = topBorder;
	}

	public Border getBottomBorder() {
		return bottomBorder;
	}

	public void setBottomBorder(Border bottomBorder) {
		this.bottomBorder = bottomBorder;
	}

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public String getFontUnderline() {
		return fontUnderline;
	}

	public void setFontUnderline(String fontUnderline) {
		this.fontUnderline = fontUnderline;
	}

	public boolean isFontBold() {
		return fontBold;
	}

	public void setFontBold(boolean fontBold) {
		this.fontBold = fontBold;
	}

	public boolean isFontItalic() {
		return fontItalic;
	}

	public void setFontItalic(boolean fontItalic) {
		this.fontItalic = fontItalic;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public AlignType getAlign() {
		return align;
	}

	public void setAlign(AlignType align) {
		this.align = align;
	}

	public VAlignType getVAlign() {
		return vAlign;
	}

	public void setVAlign(VAlignType vAlign) {
		this.vAlign = vAlign;
	}

}
