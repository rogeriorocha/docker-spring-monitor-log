package br.gov.mg.bdmg.fs.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringMapMessage;
import org.dom4j.DocumentException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.mg.bdmg.fs.dto.FileDTO;
import br.gov.mg.bdmg.fs.dto.ParamDTO;
import br.gov.mg.bdmg.fs.exception.FileServiceException;
import br.gov.mg.bdmg.fs.exception.FileUtilException;
import br.gov.mg.bdmg.fs.model.ArquivoDado;
import br.gov.mg.bdmg.fs.model.repository.ArquivoDadoRepository;
import br.gov.mg.bdmg.fs.storage.AppProperties;
import br.gov.mg.bdmg.fs.util.FilePDFUtil;
import br.gov.mg.bdmg.fs.util.FileUtil;
import br.gov.mg.bdmg.fs.util.PathUtil;
import br.gov.mg.bdmg.fs.util.StringUtil;
import br.gov.mg.bdmg.fs.util.base.FileUtilConstants.Util;

@Service
public class FSService {
	
	private final Logger LOGGER = LogManager.getLogger(this.getClass());


	private static final String IOEXCEPTION = "[IOEXCEPTION] ";
	private static final String EXCEPTION = "[EXCEPTION] ";
	private static final String STOREEXCEPTION = "[STOREEXCEPTION] ";
	private static final String FILENOTFOUNDEXCEPTION = "[FILENOTFOUNDEXCEPTION] ";
	private static final String FALHA_AO_SETAR_ENTRADA_WRITE = "Falha ao escrever entrada arquivo.";
	private static final String UNKNOW = "unknow";
	private static final String ARQUIVO_DADO_NAO_ENCONTRADO = "Arquivo Dado nao encontrado";
	private static final String ERRO_ALTERAR_STATUS = "Falha ao Alterar Status do Arquivo";
	private static final Integer CODIGO_DEFAULT = 1;
	private static final Integer CODIGO_CATEGORIA_UNION = 26;
	private static final Integer CODIGO_CATEGORIA_WATERMARK = 27;
	private static final String QUERY_MAGICA_JFS_EXPURGO = "JFS_Expurgo";

	@Autowired
	ArquivoDadoRepository arquivoDadoRpository;

	@Autowired
	AppProperties appProperties;

	public Long uploadFile(ParamDTO paramTO) throws FileServiceException, IOException {
		LOGGER.info("Class: FileServiceBean Method: uploadFile");

		ArquivoDado arquivoDadoTemp = null;
		ArquivoDado arquivoDado = null;
		Long id = null;

		try {
			Long codigoUsuarioIncl = null;
			if (!paramTO.getUsuario().isEmpty())
				codigoUsuarioIncl = Long.valueOf(paramTO.getUsuario());

			arquivoDadoTemp = ArquivoDado.builder().setFlagMigr(ArquivoDado.Flags.MIGR)
					.setAtivo(ArquivoDado.Flags.ATIVO).setCodigoUsuarioIncl(codigoUsuarioIncl).setCodigoCategoria(
							paramTO.getCodigoCategoria() == null ? CODIGO_DEFAULT : paramTO.getCodigoCategoria());

			arquivoDado = arquivoDadoRpository.save(arquivoDadoTemp);

			id = arquivoDado.getId();

			LOGGER.info("appProperties.getStorage()Location", appProperties.getStorage().getLocation());
			PathUtil file = new PathUtil(id, appProperties.getStorage().getLocation());

			File fileToSave = file.getFile(true);

			String repHash = PathUtil.saveFile(paramTO.getDadosTO().getInputStream(), fileToSave);

			arquivoDado.setTamanhoArquivo(fileToSave.length()).setHash(repHash).setDataIncl(new Date())
					.setNomeOrigem(paramTO.getDadosTO().getFileName())
					// .setCodigoUsuarioIncl(getNameUserFile(paramTO.getDadosFileTO().getUsuario()))
					.setDescricaoArquivo(paramTO.getDescricao()).setId(id).setEnderecoArquivo(fileToSave.getPath());

			arquivoDadoRpository.save(arquivoDado);

		} catch (IOException e) {

			if (arquivoDado != null) {
				arquivoDadoRpository.delete(arquivoDado);
			}
			throw new FileServiceException(IOEXCEPTION + e.getMessage(), e);
		} catch (Throwable e) {
			
			LOGGER.error(e.getMessage());
					
			if (arquivoDado != null) {
				arquivoDadoRpository.delete(arquivoDado);
			}
			throw new FileServiceException(STOREEXCEPTION + e.getMessage(), e);
		}
		return id;

	}

	/**
	 * Método responsável colocar marca d'agua em pdf e retornar id do documento.
	 *
	 * @param ParamDTO.
	 * @return Long.
	 * @exception IOException, FileNotFoundException
	 * @throws DocumentException
	 */
	public Long watermarkFile(ParamDTO paramTO) throws FileServiceException, IOException {
		LOGGER.info("Class: FileServiceBean Method: watermarkFile");

		ArquivoDado arquivoDado = null;
		Long newID = null;
		try {

			PathUtil pathUtil = new PathUtil(paramTO.getId(), appProperties.getStorage().getLocation());
			File fileOld = pathUtil.getFile(false);

			FileInputStream fis = new FileInputStream(fileOld);

			arquivoDado = ArquivoDado.builder().setFlagMigr(ArquivoDado.Flags.MIGR).setAtivo(ArquivoDado.Flags.ATIVO)
					.setCodigoCategoria(CODIGO_CATEGORIA_WATERMARK);

			arquivoDado = arquivoDadoRpository.save(arquivoDado);

			newID = arquivoDado.getId();

			File fileTmp = File.createTempFile(Util.WATERMARK_ + newID, Util.EXTENSAO_PDF);

			FileOutputStream fos = new FileOutputStream(fileTmp);

			FilePDFUtil.insertWaterMark(fis, fos, paramTO.getTexto());

			PathUtil newPath = new PathUtil(newID, appProperties.getStorage().getLocation());
			File newFile = newPath.getFile(true);

			byte[] file = FileUtil.convertToByteArray(fileTmp);

			String repHash = PathUtil.saveFile(file, newFile);

			arquivoDado.setTamanhoArquivo(newFile.length()).setHash(repHash).setDataIncl(new Date())
					.setNomeOrigem(getFileName(paramTO.getFilename(), newID))
					.setDescricaoArquivo(Util.WATERMARK + paramTO.getId()).setEnderecoArquivo(newFile.getPath());

			arquivoDado = arquivoDadoRpository.save(arquivoDado);

			fileTmp.delete();

		} catch (FileNotFoundException e) {
			LOGGER.error(new StringMapMessage().with("error", FILENOTFOUNDEXCEPTION + e.getMessage()));
			throw new FileServiceException(FILENOTFOUNDEXCEPTION + e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(new StringMapMessage().with("error", IOEXCEPTION + e.getMessage()));
			throw new FileServiceException(IOEXCEPTION + e.getMessage(), e);
		} catch (ServiceException e) {
			LOGGER.error(new StringMapMessage().with("error", STOREEXCEPTION + e.getMessage()));
			throw new FileServiceException(STOREEXCEPTION + e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error(new StringMapMessage().with("error", EXCEPTION + e.getMessage()));
			throw new FileServiceException(EXCEPTION + e.getMessage(), e);
		}

		return newID;
	}

	private String getFileName(String paramName, Long codigo) {
		if (paramName != null && !paramName.isEmpty()) {
			return paramName;
		}
		return codigo.toString() + Util.EXTENSAO_PDF;
	}

	public ArquivoDado getById(String id) {

		Optional<ArquivoDado> adOptional = arquivoDadoRpository.findById(Long.valueOf(id));
		if (!adOptional.isPresent())
			return null;
		else
			return adOptional.get();
	}

	/**
	 * Método responsável por fazer união de pdf e retornar id do novo arquivo
	 * gerado.
	 *
	 * @param ParamDTO.
	 * @return Long.
	 * @exception IOException, FileNotFoundException
	 */
	public Long unionPDFFile(ParamDTO paramTO) throws FileServiceException, IOException {
		LOGGER.info("Class: FileServiceBean Method: unionPDFFile");
		ArquivoDado arquivoDado = null;

		Long codArqNew = null;
		try {

			String dscArq = Util.UNION
					+ (paramTO.getPdf().length() > 200 ? paramTO.getPdf().substring(0, 200) : paramTO.getPdf());

			arquivoDado = ArquivoDado.builder().setFlagMigr(ArquivoDado.Flags.MIGR).setAtivo(ArquivoDado.Flags.ATIVO)
					.setCodigoCategoria(CODIGO_CATEGORIA_UNION).setDescricaoArquivo(dscArq);

			List<String> localFiles = getLocalFiles(paramTO.getPdf());

			arquivoDado = arquivoDadoRpository.save(arquivoDado);

			codArqNew = arquivoDado.getId();

			File unionFileTmp = File.createTempFile(Util.UNION_ + codArqNew, Util.EXTENSAO_PDF);

			FilePDFUtil.unionPDFs(unionFileTmp.getAbsolutePath(), localFiles);

			PathUtil pathUtil = new PathUtil(codArqNew, appProperties.getStorage().getLocation());
			File novoArquivo = pathUtil.getFile(true);

			byte[] file = FileUtil.convertToByteArray(unionFileTmp);

			String repHash = PathUtil.saveFile(file, novoArquivo);

			arquivoDado.setTamanhoArquivo(novoArquivo.length()).setHash(repHash).setDataIncl(new Date())
					.setNomeOrigem(getFileName(paramTO.getFilename(), codArqNew))
					.setEnderecoArquivo(novoArquivo.getPath());

			arquivoDado = arquivoDadoRpository.save(arquivoDado);

			unionFileTmp.delete();

		} catch (FileNotFoundException e) {
			
			LOGGER.error(new StringMapMessage().with("error", FILENOTFOUNDEXCEPTION + e.getMessage()));
			throw new FileServiceException(FILENOTFOUNDEXCEPTION + e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(new StringMapMessage().with("error", IOEXCEPTION + e.getMessage()));
			throw new FileServiceException(IOEXCEPTION + e.getMessage(), e);
		} catch (ServiceException e) {
			LOGGER.error(new StringMapMessage().with("error", STOREEXCEPTION + e.getMessage()));
			throw new FileServiceException(STOREEXCEPTION + e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error(new StringMapMessage().with("error", EXCEPTION + e.getMessage()));
			throw new FileServiceException(EXCEPTION + e.getMessage(), e);
		}

		return codArqNew;

	}

	private List<String> getLocalFiles(String pdf) throws IOException {

		List<String> localFiles = new ArrayList<String>();
		String[] files = pdf.split(",");
		Long id = null;

		try {
			for (String codigoArquivo : files) {
				id = Long.valueOf(codigoArquivo);
				PathUtil pathUtil = new PathUtil(id, appProperties.getStorage().getLocation());
				File file = pathUtil.getFile(false);
				localFiles.add(file.getAbsolutePath());
			}
		} catch (IOException e) {
			LOGGER.error(new StringMapMessage().with("error", "Codigo Arquivo não encontrado: " + id + " "+e.getMessage() ));
			throw new IOException("Codigo Arquivo não encontrado: " + id);
		}
		return localFiles;

	}

	public byte[] encodeFile(String fromEncode, String toEncode, File file) {

		return null;

	}
	

	private void setDecodingFile(String fromEncode, String toEncode, File file, FileDTO fileTO)
			throws FileNotFoundException, IOException, FileUtilException {
		if (StringUtil.nonEmpty(fromEncode)) {
			InputStream inputStream = new FileInputStream(file);

			byte[] byteFile = IOUtils.toByteArray(inputStream);
			String decoded = FileUtil.decodedFile(fromEncode, byteFile);
			if (StringUtil.nonEmpty(toEncode)) {
				byte[] bsEncoded = FileUtil.encodedFile(toEncode, decoded);
				fileTO.setFile(bsEncoded);
			} else {
				fileTO.setFile(decoded.getBytes());
			}
		}
	}
	

	public byte[] download(Long id, String fromEncode, String toEncode) throws IOException, FileUtilException {
		
		PathUtil pathUtil = new PathUtil(id, appProperties.getStorage().getLocation());
		
		File file = pathUtil.getFile(false);
		
		FileDTO fileTO = new FileDTO();
		
		fileTO.setFile(FileUtil.convertToByteArray(file));
		//fileTO.setNameFile(arquivoDado.getNomeOrigem());

		setDecodingFile(fromEncode,toEncode, file, fileTO);
		

		return fileTO.getFileBytes();

	}

	public long expurgar() {

		List<ArquivoDado> lst = arquivoDadoRpository
				.findTop10ByAtivoAndDataExpurgoLessThanEqualOrderByDataInclAsc(ArquivoDado.Flags.ATIVO, new Date());

		for (Iterator<ArquivoDado> it = lst.iterator(); it.hasNext();) {
			ArquivoDado arquivoDado = it.next();
			
			PathUtil pathUtil = new PathUtil(arquivoDado.getId(), appProperties.getStorage().getLocation());
			pathUtil.delete();

			arquivoDado.setAtivo(ArquivoDado.Flags.INATIVO);
			arquivoDadoRpository.save(arquivoDado);
			

			System.out.println("DELETE " + arquivoDado.getId());
		}

		return lst.size();
	}

	public void healthcheck() throws Exception {
		
		File file = new File(appProperties.getStorage().getLocation(), "helthcheck.txt");
		
		if (!file.exists()) {
			LOGGER.error("healthcheck file helthcheck.txt not found!");
			throw new Exception("healthcheck file helthcheck.txt not found!");
		}
		
		/*
		MessageDigest digest = MessageDigest.getInstance(FileUtilConstants.CryptographicHash.MD5);
		
		digest.update("ONLY HEATHCHCK TEST - NO CHANGE NEVER".getBytes(StandardCharsets.UTF_8));
		
		byte[] hashedBytes = digest.digest();
		
		String hashMD5 = EncryptionUtil.toHex(hashedBytes);
		*/
		
		
		
		
		
		
		
		
		
		
		
	}

}