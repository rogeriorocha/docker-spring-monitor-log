package br.gov.mg.bdmg.fs.dto;

import java.io.Serializable;

public class InfoResource implements Serializable {

	private static final long serialVersionUID = 1L;

	private String filename;
	private Long id;
	private String hash;
	private String ativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@Override
    public String toString() {
        return "FileDataResponseDTO {" +
                "id='" + id + '\'' +
                ", filename='" + filename + '\'' +
                ", hash='" + hash + '\'' +
                ", ativo ='" + ativo + '\'' +
                '}';
    }	

}
