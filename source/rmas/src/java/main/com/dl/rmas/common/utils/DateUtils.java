package com.dl.rmas.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	public static DateFormat getDateFormat(String formatString) {
		return new SimpleDateFormat(formatString);
    }

	/**
	 * <b>Function: <b>date 转换成 pattern 类型字符串
	 *
	 * @param src
	 * @param pattern	例如：“yyyy-MM-dd HH:mm:ss”
	 * @return
	 */
	public static String transferDate2Str(Date src, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(src);
	}
	
	/**
	 * 把date格式化为'YYMMDD'格式字符串
	 *
	 * @param date
	 * @return
	 */
	public static String formateToYYMMDD(Date date) {
		return getDateFormat("yyMMdd").format(date);
	}
	
	/**
	 * 把date格式化为'dd-MM-yyyy'格式字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String formateToDDMMYYYY(Date date) {
		return getDateFormat("dd-MM-yyyy").format(date);
	}
	
	/**
	 * 把 date 格式化为 'yyyy-MM-dd' 格式字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String formateToYYYYMMDD(Date date) {
		return getDateFormat("yyyy-MM-dd").format(date);
	}
	
	public static void main(String[] args) {
		System.out.println(formateToYYMMDD(new Date()));
	}
	
}
