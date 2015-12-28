package com.dl.core.jxls.common.model;
/**
 * 可参数化类
 * @author Administrator
 *
 */
public interface Parameterizable {
	/**
	 * 得到存放的参数
	 * @return
	 */
	public ParameterSet parameters();

	/**
	 * 设置参数
	 * @param parameters
	 */
	public void setParameters(ParameterSet parameters);
}
