package br.gov.mg.bdmg.fs.dto;

import java.io.Serializable;

public class FileDataResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String filename;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}