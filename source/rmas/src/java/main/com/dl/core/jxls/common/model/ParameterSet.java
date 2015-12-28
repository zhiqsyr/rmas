package com.dl.core.jxls.common.model;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;
/**
 * 参数存放类，保存的参数类型为{string key=string value}
 * @author dinguangx
 *
 */
public class ParameterSet {
	private Hashtable<String,String> paramMap = new Hashtable<String, String>();
	
	/**
	 * 新增参数
	 * @param key,键不能为null
	 * @param value
	 */
	public void setParameter(String key,String value){
		if(key == null){
			throw new IllegalArgumentException("参数名称不能为空.");
		}
		if(paramMap == null){
			paramMap = new Hashtable<String, String>();
		}
		paramMap.put(key, value);
	}
	/**
	 * 复制参数
	 */
	public void addParameters(ParameterSet parameters){
		if(parameters.paramMap == null){
			return;
		}
		for(Entry<String,String> entry: parameters.paramMap.entrySet()){
			setParameter(entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * 得到参数值
	 * @param key
	 * @return
	 */
	public String getParameter(String key){
		if(paramMap == null || key == null){
			return null;
		}
		return paramMap.get(key);
	}
	
	/**
	 * 参数名列表
	 * @return
	 */
	public Set<String> parameterNames(){
		if(paramMap == null){
			paramMap = new Hashtable<String, String>();
		}
		return paramMap.keySet();
	}
	
	@Override
	public String toString() {
		return paramMap.toString();
	}
}
