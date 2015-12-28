package com.dl.core.jxls.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.dl.core.jxls.service.store.FileStoreService;
import com.dl.core.jxls.util.SpringContextHolder;

@Entity
@Table(name = "EXCEL_FILE_SAVE")
public class FileSave implements Serializable {
	private static final long serialVersionUID = -4262295944260048950L;
	
	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "uuid")
	@GeneratedValue(generator = "uuidGenerator")
	@Column(name = "id")
	private String id;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "file_name")
	private String fileName;
	
	@Column(name = "file_save_path")
	private String fileSavePath;
	
	@Column(name = "upload_time")
	private Date uploadTime;
	
	@Column(name = "report_type")
	private String reportType;
	
	@Column(name = "is_success")
	private Boolean isSuccess;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSavePath() {
		return fileSavePath;
	}

	public void setFileSavePath(String fileSavePath) {
		this.fileSavePath = fileSavePath;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * 获取文件的输出流
	 * 
	 * @return
	 */
	public InputStream getFileInputStream() {
		FileStoreService storeService = SpringContextHolder
				.getBean(FileStoreService.class);
		String dir = storeService.getSaveDir();
		File file = new File(dir, this.getFileSavePath());
		try {
			return new FileInputStream(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
