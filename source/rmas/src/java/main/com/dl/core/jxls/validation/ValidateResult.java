package com.dl.core.jxls.validation;

import java.io.Serializable;

/**
 * 数据校验结果
 * 
 * @author dylan
 * @date 2012-8-20 下午1:09:28
 */
public class ValidateResult implements Serializable {
	private static final long serialVersionUID = -6842999828365763733L;
	private String msg;
	private boolean success = true;

	public ValidateResult() {
	}

	public ValidateResult(boolean success, String msg) {
		setSuccess(success).setMsg(msg);
	}

	public String getMsg() {
		return msg;
	}

	public ValidateResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public ValidateResult setSuccess(boolean result) {
		this.success = result;
		return this;
	}

}