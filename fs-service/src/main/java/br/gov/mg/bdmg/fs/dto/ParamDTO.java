package br.gov.mg.bdmg.fs.dto;

import java.io.Serializable;

public class ParamDTO implements Serializable {

	private static final long serialVersionUID = 6560312650771865526L;

	private String hostClient;

	private Long id;

	private String fromEncode;

	private String toEncode;

	private String compactar;

	private String usuario;

	private String filenameSet;

	private Integer codigoCategoria;

	private String host;

	private DadosDTO dadosTO;

	private DadosFileDTO dadosFileTO = new DadosFileDTO();

	private String texto;

	private String pdf;

	private String filename;
	
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public ParamDTO setDescricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	public static ParamDTO builder() {
		return new ParamDTO();
	}

	public String getHost() {
		return host;
	}

	public ParamDTO setHost(String host) {
		this.host = host;
		return this;
	}

	public String getFilenameSet() {
		return filenameSet;
	}

	public ParamDTO setFilenameSet(String filenameSet) {
		this.filenameSet = filenameSet;
		return this;
	}

	public String getUsuario() {
		return usuario;
	}

	public ParamDTO setUsuario(String usuario) {
		this.usuario = usuario;
		return this;
	}

	public String getHostClient() {
		return hostClient;
	}

	public ParamDTO setHostClient(String hostClient) {
		this.hostClient = hostClient;
		return this;
	}

	public Long getId() {
		return id;
	}

	public ParamDTO setId(Long id) {
		this.id = id;
		return this;
	}

	public String getFromEncode() {
		return fromEncode;
	}

	public ParamDTO setFromEncode(String fromEncode) {
		this.fromEncode = fromEncode;
		return this;
	}

	public String getToEncode() {
		return toEncode;
	}

	public ParamDTO setToEncode(String toEncode) {
		this.toEncode = toEncode;
		return this;
	}

	public String getCompactar() {
		return compactar;
	}

	public ParamDTO setCompactar(String compactar) {
		this.compactar = compactar;
		return this;
	}

	public Integer getCodigoCategoria() {
		return codigoCategoria;
	}

	public ParamDTO setCodigoCategoria(Integer codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
		return this;
	}

	public DadosDTO getDadosTO() {
		return dadosTO;
	}

	public ParamDTO setDadosTO(DadosDTO dadosTO) {
		this.dadosTO = dadosTO;
		return this;
	}

	public DadosFileDTO getDadosFileTO() {
		return dadosFileTO;
	}

	public ParamDTO setDadosFileTO(DadosFileDTO dadosFileTO) {
		this.dadosFileTO = dadosFileTO;
		return this;
	}

	public String getTexto() {
		return texto;
	}

	public ParamDTO setTexto(String texto) {
		this.texto = texto;
		return this;
	}

	public String getPdf() {
		return pdf;
	}

	public ParamDTO setPdf(String pdf) {
		this.pdf = pdf;
		return this;
	}

	public String getFilename() {
		return filename;
	}

	public ParamDTO setFilename(String filename) {
		this.filename = filename;
		return this;
	}

}