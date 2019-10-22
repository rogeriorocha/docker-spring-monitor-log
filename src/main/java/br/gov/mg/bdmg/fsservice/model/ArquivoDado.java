package br.gov.mg.bdmg.fsservice.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "arquivo_dados", schema = "dbo", catalog = "bdseg")

public class ArquivoDado implements Serializable  {

	private static final long serialVersionUID = 1L;


	public interface Flags {
		String MIGR = "S";
		String ATIVO = "S";
		String INATIVO = "N";
	}

	@Id
	@Column(name = "cod_arq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "end_arq")
	private String enderecoArquivo;

	@Column(name = "nom_orig")
	private String nomeOrigem;

	@Column(name = "cod_categ")
	private Integer codigoCategoria;

	@Column(name = "cod_usu_incl")
	private Long codigoUsuarioIncl;

	@Column(name = "dat_incl")
	private Date dataIncl;
	
	@Column(name = "dat_expur")
	private Date dataExpurgo;

	@Column(name = "dsc_arq")
	private String descricaoArquivo;

	@Column(name = "tam_arq")
	private Long tamanhoArquivo;

	@Column(name = "flg_migr")
	private String flagMigr;

	@Column(name = "cod_algtm_hash")
	private String hash;

	@Column(name = "flg_ati")
	private String ativo;

	public Long getId() {
		return id;
	}

	public static ArquivoDado builder() {
		return new ArquivoDado();
	}

	public ArquivoDado setId(Long id) {
		this.id = id;
		return this;
	}

	public String getEnderecoArquivo() {
		return enderecoArquivo;
	}

	public ArquivoDado setEnderecoArquivo(String enderecoArquivo) {
		this.enderecoArquivo = enderecoArquivo;
		return this;
	}

	public String getNomeOrigem() {
		return nomeOrigem;
	}

	public ArquivoDado setNomeOrigem(String nomeOrigem) {
		this.nomeOrigem = nomeOrigem;
		return this;
	}

	public Integer getCodigoCategoria() {
		return codigoCategoria;
	}

	public ArquivoDado setCodigoCategoria(Integer codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
		return this;
	}

	public Long getCodigoUsuarioIncl() {
		return codigoUsuarioIncl;
	}

	public ArquivoDado setCodigoUsuarioIncl(Long codigoUsuarioIncl) {
		this.codigoUsuarioIncl = codigoUsuarioIncl;
		return this;
	}

	public Date getDataIncl() {
		return dataIncl;
	}

	public ArquivoDado setDataIncl(Date dataIncl) {
		this.dataIncl = dataIncl;
		return this;
	}

	public String getDescricaoArquivo() {
		return descricaoArquivo;
	}

	public ArquivoDado setDescricaoArquivo(String descricaoArquivo) {
		this.descricaoArquivo = descricaoArquivo;
		return this;
	}

	public Long getTamanhoArquivo() {
		return tamanhoArquivo;
	}

	public ArquivoDado setTamanhoArquivo(Long tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
		return this;
	}

	public String getFlagMigr() {
		return flagMigr;
	}

	public ArquivoDado setFlagMigr(String flagMigr) {
		this.flagMigr = flagMigr;
		return this;
	}

	public String getHash() {
		return hash;
	}

	public ArquivoDado setHash(String hash) {
		this.hash = hash;
		return this;
	}

	public String getAtivo() {
		return ativo;
	}

	public ArquivoDado setAtivo(String ativo) {
		this.ativo = ativo;
		return this;
	}

	@Override
	public String toString() {
		return "ArquivoDado [id=" + id + ", nomeOrigem=" + nomeOrigem + ", codigoCategoria=" + codigoCategoria + ", dataIncl=" + dataIncl
				+ ", tamanhoArquivo=" + tamanhoArquivo + ", hash=" + hash + "]";
	}

	/*
	@Override
	public boolean isNew() {
		return id == null;
	}
  */	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArquivoDado other = (ArquivoDado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}