package me.smulyono.mppconverter.model;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadForm {
	// Uploaded MPX MPP File
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
