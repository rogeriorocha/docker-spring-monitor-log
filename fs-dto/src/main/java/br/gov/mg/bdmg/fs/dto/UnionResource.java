package br.gov.mg.bdmg.fs.dto;

import java.io.Serializable;

public class UnionResource implements Serializable {


	private static final long serialVersionUID = 6940615026678285512L;
	
	private Long id;
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
    public String toString() {
		StringBuilder sb = new StringBuilder("UnionResource {")
		  .append("id").append(id)
		  .append(",erroMessage").append(errorMessage)
		  .append("}");
		
		return sb.toString();
    }		

}
