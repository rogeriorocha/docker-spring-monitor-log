package br.gov.mg.bdmg.fs.dto;

import java.io.Serializable;

public class ExpurgoResource implements Serializable {

	private static final long serialVersionUID = 1L;

	private String errorMessage;
	private long qtdeDeletada;
	

	public long getQtdeDeletada() {
		return qtdeDeletada;
	}

	public void setQtdeDeletada(long qtdeDeletada) {
		this.qtdeDeletada = qtdeDeletada;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
