package com.dl.rmas.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

import com.dl.rmas.common.exception.SystemException;

public class PropertiesUtils {

	private static final ResourceBundle system = ResourceBundle.getBundle("Config/properties/i18n/system");
	private static final ResourceBundle enums = ResourceBundle.getBundle("Config/properties/i18n/enums");
	
	/**
	 * Config/properties/i18n/system.properties
	 * 
	 * @param key
	 * @return
	 */
	public static String getValueInSystem(String key) {
		return getValue(system, key);
	}
	
	/**
	 * Config/properties/i18n/enums.properties
	 * 
	 * @param key
	 * @return
	 */
	public static String getValueInEnums(String key) {
		return getValue(enums, key);
	}
	
	private static String getValue(ResourceBundle rb, String key) {
		String value = rb.getString(key);
		
		try {
			return new String(value.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new SystemException(e.getMessage());
		}
	}
	
}
