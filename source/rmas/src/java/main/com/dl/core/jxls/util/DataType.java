package com.dl.core.jxls.util;

/**
 * @author Lucas
 * @date 2014-12-22 上午10:12:51
 */
public final class DataType {
	private DataType() {
	}

	public static boolean isNumberType(int dataType) {

		return 2 <= dataType && dataType <= 8;
	}

	public static boolean isDateType(int dataType) {

		return 10 <= dataType && dataType <= 12;
	}

	public static boolean isBaseDataType(Class clazz) {
		return clazz.isPrimitive() || clazz.equals(java.lang.String.class)
				|| (java.lang.Number.class).isAssignableFrom(clazz)
				|| (java.lang.Boolean.class).isAssignableFrom(clazz)
				|| (java.util.Date.class).isAssignableFrom(clazz);
	}

	public static int parse(Class clazz) {
		if (clazz.equals(java.lang.String.class))
			return 1;
		if (clazz.equals(Integer.TYPE))
			return 4;
		if (clazz.equals(Boolean.TYPE))
			return 9;
		if (clazz.equals(Float.TYPE))
			return 6;
		if (clazz.equals(java.util.Date.class))
			return 10;
		if (clazz.equals(java.sql.Date.class))
			return 10;
		if (clazz.equals(java.sql.Time.class))
			return 11;
		if (clazz.equals(java.sql.Timestamp.class))
			return 12;
		if (clazz.equals(Long.TYPE))
			return 5;
		if (clazz.equals(Double.TYPE))
			return 7;
		if (clazz.equals(Byte.TYPE))
			return 2;
		if (clazz.equals(Short.TYPE))
			return 3;
		if (clazz.equals(java.math.BigDecimal.class))
			return 8;
		if (clazz.equals(java.lang.Integer.class))
			return 4;
		if (clazz.equals(java.lang.Boolean.class))
			return 9;
		if (clazz.equals(java.lang.Float.class))
			return 6;
		if (clazz.equals(java.lang.Long.class))
			return 5;
		if (clazz.equals(java.lang.Double.class))
			return 7;
		if (clazz.equals(java.lang.Byte.class))
			return 2;
		return !clazz.equals(java.lang.Short.class) ? 1 : 3;
	}

	public static int nameToType(String name) {
		if ("string".equals(name))
			return 1;
		if ("boolean".equals(name))
			return 9;
		if ("int".equals(name))
			return 4;
		if ("float".equals(name))
			return 6;
		if ("date".equals(name))
			return 10;
		if ("time".equals(name))
			return 11;
		if ("datetime".equals(name))
			return 12;
		if ("double".equals(name))
			return 7;
		if ("long".equals(name))
			return 5;
		if ("byte".equals(name))
			return 2;
		if ("short".equals(name))
			return 3;
		if ("bigdecimal".equals(name))
			return 8;
		return !"binary".equals(name) ? 0 : 20;
	}

	public static String typeToName(int type) {
		switch (type) {
		case 1:
			return "string";

		case 9:
			return "boolean";

		case 4:
			return "int";

		case 6:
			return "float";

		case 10:
			return "date";

		case 11:
			return "time";

		case 12:
			return "datetime";

		case 7:
			return "double";

		case 5:
			return "long";

		case 2:
			return "byte";

		case 3:
			return "short";

		case 8:
			return "bigdecimal";

		case 20:
			return "binary";

		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
		default:
			return "";
		}
	}

	public static Class typeToClass(int type) {
		switch (type) {
		case 1:
			return java.lang.String.class;

		case 9:
			return java.lang.Boolean.class;

		case 4:
			return java.lang.Integer.class;

		case 6:
			return java.lang.Float.class;

		case 10:
		case 11:
		case 12:
			return java.util.Date.class;

		case 7:
			return java.lang.Double.class;

		case 5:
			return java.lang.Long.class;

		case 2:
			return java.lang.Byte.class;

		case 3:
			return java.lang.Short.class;

		case 8:
			return java.math.BigDecimal.class;

		case 20:
			return null;

		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
		default:
			return null;
		}
	}

	public static final String UNKNOWN_NAME = "";
	public static final int UNKNOWN = 0;
	public static final String STRING_NAME = "string";
	public static final int STRING = 1;
	public static final String BYTE_NAME = "byte";
	public static final int BYTE = 2;
	public static final String SHORT_NAME = "short";
	public static final int SHORT = 3;
	public static final String INT_NAME = "int";
	public static final int INT = 4;
	public static final String LONG_NAME = "long";
	public static final int LONG = 5;
	public static final String FLOAT_NAME = "float";
	public static final int FLOAT = 6;
	public static final String DOUBLE_NAME = "double";
	public static final int DOUBLE = 7;
	public static final String BIGDECIMAL_NAME = "bigdecimal";
	public static final int BIGDECIMAL = 8;
	public static final String BOOLEAN_NAME = "boolean";
	public static final int BOOLEAN = 9;
	public static final String DATE_NAME = "date";
	public static final int DATE = 10;
	public static final String TIME_NAME = "time";
	public static final int TIME = 11;
	public static final String DATETIME_NAME = "datetime";
	public static final int DATETIME = 12;
	public static final String BINARY_NAME = "binary";
	public static final int BINARY = 20;
}
