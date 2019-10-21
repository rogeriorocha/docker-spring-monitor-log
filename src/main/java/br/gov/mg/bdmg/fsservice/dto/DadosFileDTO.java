package br.gov.mg.bdmg.fsservice.dto;


import java.io.Serializable;

public class DadosFileDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8067198162046118211L;
	private String usuario;
	private String descricao;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}