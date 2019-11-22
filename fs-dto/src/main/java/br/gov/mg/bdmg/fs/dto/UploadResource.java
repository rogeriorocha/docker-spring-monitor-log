package br.gov.mg.bdmg.fs.dto;

import java.io.Serializable;

public class UploadResource implements Serializable {


	private static final long serialVersionUID = 6940615026678285512L;
	
	
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	private String errorMessage;
}
