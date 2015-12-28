package com.dl.core.jxls.util;

import it.sauronsoftware.base64.Base64;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 字符串工具类
 * @author dylan
 * @date 2013-5-13 上午10:03:04
 */
public class StringHelper {
	/**
	 * 判断字符串为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 判断字符串不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	public static String isoToUtf(String str) {

		if (str == null)
			return null;
		String temp = null;
		try {
			temp = new String(str.getBytes(), "iso8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return temp;
	}

	public static String utfToIso(String str) {

		if (str == null)
			return null;
		String temp = null;
		try {
			temp = new String(str.getBytes("utf-8"), "iso8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return temp;
	}

	public static String isoToGBK(String str) {

		if (str == null)
			return null;
		String temp = null;
		try {
			temp = new String(str.getBytes("iso8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 计算字符长度
	 * 
	 * @param str
	 * @return
	 */
	public static int getStrLen(String str) {
		if (str == null || "".equals(str.trim()))
			return 0;
		int size = 0;
		String regEx = "[^x00-xff]"; // 匹配双字节
		Pattern p = Pattern.compile(regEx);
		int length = (str == null) ? 0 : str.length();
		for (int i = 0; i < length; i++) {
			char temp = str.charAt(i);
			Matcher m = p.matcher(String.valueOf(temp));
			boolean result = m.find();
			if (result) {
				size += 2;
			} else {
				size += 1;
			}
		}
		return size;
	}
	
	public static String enCode(String str){
		String newName = Base64.encode(str);
		newName = newName.replace("+", "*");
		newName = newName.replace("/", "-");
		return newName;
	}
	
	public static String deCode(String str){
		String newName = str.replace("*", "+");
		newName = newName.replace("-", "/");
		return newName = Base64.decode(newName);
	}
	
	/**
	 * 将字符转换为html字符
	 * 
	 * @param c
	 * @return
	 */
	public static String escapeHTML(char c) {
		switch (c) {
		case ' ':
			return "&nbsp;";
		case '<':
			return "&lt;";
		case '>':
			return "&gt;";
		case '&':
			return "&amp;";
		case '\"':
			return "&quot;";
		}
		return String.valueOf(c);
	}
	/**
	 * 将string转换为boolean类型
	 * @param s
	 * @return
	 */
	public static boolean parseBoolean(String s) {
		if (s == null)
			return false;
		else
			return s.equalsIgnoreCase("true") || s.equals("1")
					|| s.equals("-1") || s.equalsIgnoreCase("T")
					|| s.equalsIgnoreCase("Y");
	}
}
