package br.gov.mg.bdmg.fsservice.dto;

import java.io.IOException;

import org.springframework.http.HttpHeaders;

public class InputDataFileDTO {

	public interface Dados {
		String USER = "user";
		String USUARIO = "usuario";
		String HOST = "host";
		String PASSWORD = "password";
		String HASH = "hash";
		String FILE_NAME = "filename";
		String DESCRICAO = "descricao";
		String ARQUIVO = "arquivo";
	}

	private static final String FALHA_RECUPERAR_DADOS_ARQUIVO = "Falha ou recuperar dados arquivo.";
	

	private HttpHeaders headers;

	private String host;

	private String user;

	public InputDataFileDTO() {
		super();
	}

	public InputDataFileDTO(HttpHeaders headers) {
		this.headers = headers;
		//this.multipartFormDataInput = multipartFormDataInput;
	}



	public DadosDTO getDadosTO() throws IOException {
		DadosDTO dadosTO = new DadosDTO();
		/*
		 * try { Map<String, List<InputPart>> uploadForm =
		 * multipartFormDataInput.getFormDataMap(); List<InputPart> lstFile =
		 * uploadForm.get(Dados.ARQUIVO); if (lstFile != null) { InputPart inputPart =
		 * lstFile.get(Decimal.ZERO); MultivaluedMap<String, String> header =
		 * inputPart.getHeaders(); InputStream inputStream =
		 * inputPart.getBody(InputStream.class, null);
		 * 
		 * dadosTO.setFileName(getFileName(header));
		 * dadosTO.setInputStream(FileUtil.convertToByteArray(inputStream)); } } catch
		 * (IOException e) { throw new IOException(FALHA_RECUPERAR_DADOS_ARQUIVO); }
		 */
		return dadosTO;
	}

	public DadosFileDTO getDadosFileTO() throws IOException {
		DadosFileDTO dadosFileTO = new DadosFileDTO();
		/*
		 * Map<String, List<InputPart>> uploadForm =
		 * multipartFormDataInput.getFormDataMap();
		 * 
		 * List<InputPart> lstUsuario = uploadForm.get(Dados.USUARIO); List<InputPart>
		 * lstDescricao = uploadForm.get(Dados.DESCRICAO); try { if (lstUsuario != null)
		 * { dadosFileTO.setUsuario(lstUsuario.get(Decimal.ZERO).getBodyAsString()); }
		 * if (lstDescricao != null) {
		 * dadosFileTO.setDescricao(lstDescricao.get(Decimal.ZERO).getBodyAsString()); }
		 * } catch (IOException e) { throw new
		 * IOException(FALHA_RECUPERAR_DADOS_ARQUIVO); } 
		 */
		return dadosFileTO;
	}

	/*
	 * private String getFileName(MultivaluedMap<String, String> header) { final
	 * String SPLIT_CONTENT_DISPOSITION = ";"; final String CONTENT_DISPOSITION =
	 * "Content-Disposition"; final String SPLIT_FILE_NAME = "="; final String
	 * REGEX_FILE_NAME = "\"";
	 * 
	 * String[] contentDisposition =
	 * header.getFirst(CONTENT_DISPOSITION).split(SPLIT_CONTENT_DISPOSITION); for
	 * (String filename : contentDisposition) { if
	 * ((filename.trim().startsWith(Dados.FILE_NAME))) { String[] name =
	 * filename.split(SPLIT_FILE_NAME); String finalFileName =
	 * name[Decimal.ONE].trim().replaceAll(REGEX_FILE_NAME, StringUtils.EMPTY);
	 * return finalFileName; } } return FileUtilConstants.Util.UNKNOW; }
	 */
	/*
	 * public String getHost() { if (headers != null) { List<String> aux =
	 * headers.getRequestHeaders().get(Dados.HOST); if (aux != null) { host =
	 * (String) aux.get(Decimal.ZERO); } } return host; }
	 */
	/*
	 * public String getUser() { if (headers != null) { List<String> aux =
	 * headers.getRequestHeaders().get(Dados.USER); if (aux != null) { user =
	 * (String) aux.get(Decimal.ZERO); } } return user; }
	 */
}	