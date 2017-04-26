package com.dl.core.jxls.service.store;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.dl.core.jxls.dao.FileSaveDao;
import com.dl.core.jxls.entity.FileSave;

/**
 * 文件上传之后的保存组件，通过参数report_type获取当前上传文件的项目类型
 * 
 * @author dylan
 * @date 2012-9-28 下午2:51:31
 */
@Component("upload.fileStoreService")
public class FileStoreService implements InitializingBean {
	/**
	 * 数据文件保存的目录
	 */
	@Value("${excel.upload.savedir}")
	private String saveDir;
	@Value("${excel.upload.subdir.pattern}")
	private String subdirPattern = "{yyyyMM}";

	private String patternText = null;
	private List<Object> paramsPattern = new ArrayList<Object>();

	@Autowired
	private FileSaveDao fileSaveDao;

	/**
	 * 保存上传的Excel文件，返回构造好的数据保存模型
	 * 
	 * @param file
	 * @param model
	 * @param reportType
	 * @return
	 */
	public FileSave saveUploadFile(MultipartFile file, Model model,
			String reportType) {
		FileSave entity = new FileSave();
		entity.setFileName(FilenameUtils.getName(file.getOriginalFilename()));
//		entity.setUserId(AppUtil.getLoginUserName());
		entity.setReportType(reportType);
		entity.setUploadTime(new Date());

		String subDir = getSubDir(entity);

		File fileDir = new File(getSaveDir(), subDir);
		try {
			FileUtils.forceMkdir(fileDir);
		} catch (IOException e) {
			throw new RuntimeException("创建保存目录失败.");
		}
		String newName = UUID.randomUUID().toString() + "."
				+ FilenameUtils.getExtension(file.getOriginalFilename());
		entity.setFileSavePath(subDir + "/" + newName);

		// save to local disk
		try {
			file.transferTo(new File(fileDir, newName));
		} catch (Exception e) {
			throw new RuntimeException("保存文件失败.");
		}
		fileSaveDao.save(entity);
		return entity;
	}

	/**
	 * 创建文件保存的子目录
	 * 
	 * @return
	 */
	protected String getSubDir(FileSave entity) {

		String reportType = entity.getReportType();
		Date date = entity.getUploadTime();
		List<String> params = new ArrayList<String>();

		for (Object p : paramsPattern) {
			if (p instanceof SimpleDateFormat) {
				params.add(((SimpleDateFormat) p).format(date));
			} else if ("reportType".equals(p)) {
				params.add(reportType);
			}
		}

		return MessageFormat.format(patternText, params.toArray());
	}

	public void setSubdirPattern(String subdirPattern) {
		this.subdirPattern = subdirPattern;
	}

	public String getSubdirPattern() {
		return subdirPattern;
	}

	public void setSaveDir(String saveDir) {
		this.saveDir = saveDir;
	}

	public String getSaveDir() {
		return saveDir;
	}

	public void setFileSaveDao(FileSaveDao fileSaveDao) {
		this.fileSaveDao = fileSaveDao;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// 解析日期格式
		String pattern = subdirPattern.trim();
		int params = 0;
		StringBuilder sb = new StringBuilder();

		boolean inbrace = false;

		StringBuilder subPattern = new StringBuilder();
		for (int i = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			if (c == '{') {
				inbrace = true;
				subPattern = new StringBuilder();
			} else if (c == '}') {
				inbrace = false;
				if ("reportType".equals(subPattern.toString())) {
					paramsPattern.add("reportType");
				} else {
					paramsPattern.add(new SimpleDateFormat(subPattern
							.toString()));
				}
				sb.append('{').append(params++).append('}');
			} else if (inbrace) {
				subPattern.append(c);
			} else {
				sb.append(c);
			}
		}
		patternText = sb.toString();
	}

	public static void main(String[] args) throws Exception {
		FileSave entity = new FileSave();
		entity.setUploadTime(new Date());
		entity.setReportType("test_type");

		String patterns[] = new String[] { "{yyyyMM}/{reportType}",
				"{reportType}/{yyyyMMdd}", "{reportType}/{yyyyMM}",
				"{reportType}/{yyyy}/{MM}/{dd}" };

		for (String pattern : patterns) {

			FileStoreService storeService = new FileStoreService();
			storeService.subdirPattern = pattern;
			storeService.afterPropertiesSet();
			System.out.println(storeService.getSubDir(entity));
		}

	}
}
