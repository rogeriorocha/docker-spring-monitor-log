package br.gov.mg.bdmg.fsservice.dto;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import br.gov.mg.bdmg.fsservice.util.FileUtil;

public class FileDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2885463527628611639L;

	private byte[] file;

	private String nameFile;

	public FileDTO() {
		super();
	}

	public FileDTO(byte[] file, String nameFile) {
		super();
		this.file = file;
		this.nameFile = nameFile;
	}

	public File getFile() throws IOException {
		return FileUtil.convertToFile(file, nameFile);
	}

	public byte[] getFileBytes() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

}
