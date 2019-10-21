package br.gov.mg.bdmg.fsservice.dto;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;


public class DadosDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -134376066944906401L;

	private String fileName;
	
	private byte[] inputStream;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getInputStream() {
		return inputStream;
	}

	public void setInputStream(byte[] inputStream) {
		this.inputStream = inputStream;
	}
	
	public void setFile(File file) throws FileNotFoundException, IOException {
		setInputStream(IOUtils.toByteArray(new FileInputStream(file)));
	}

}