package com.dl.core.jxls.validation.util;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
/**
 * 数据校验的资源信息管理类
 * @author dylan
 * @date 2013-1-24 下午1:23:30
 */
@Component
public class ValidateResourceBundle {

	@Resource
	private MessageSource messageSource;

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public String getMessage(String key, Object[] args) {
		return messageSource.getMessage(key, args, Locale.getDefault());
	}

	public String getMessage(String key, Object[] args, String defaultMsg) {
		return messageSource.getMessage(key, args, defaultMsg,
				Locale.getDefault());
	}

	public String getMessage(String key) {
		return messageSource.getMessage(key, null, Locale.getDefault());
	}

	public String getMessage(String key, String defaultMsg) {
		return messageSource.getMessage(key, null, defaultMsg,
				Locale.getDefault());
	}
}
