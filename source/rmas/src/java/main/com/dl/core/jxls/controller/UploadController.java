package com.dl.core.jxls.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dl.core.jxls.service.store.FileStoreService;

/**
 * 
 * @author dylan
 * @date 2012-9-27 下午4:05:14
 */
@Component("upload.controller")
@RequestMapping("/excel/")
public class UploadController {

	@Autowired
	private FileStoreService fileStoreService;

	public void setFileStoreService(FileStoreService fileStoreService) {
		this.fileStoreService = fileStoreService;
	}

	public FileStoreService getFileStoreService() {
		return fileStoreService;
	}
}
