package com.dl.core.jxls.export.model;

import java.io.InputStream;

public class FileEntry {
	private String filename;
	private InputStream inputstream;

	public FileEntry(String filename, InputStream inputstream) {
		this.filename = filename;
		this.inputstream = inputstream;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public InputStream getInputstream() {
		return inputstream;
	}

	public void setInputstream(InputStream inputstream) {
		this.inputstream = inputstream;
	}

}
