package br.gov.mg.bdmg.fsservice.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.gov.mg.bdmg.fsservice.dto.DadosDTO;
import br.gov.mg.bdmg.fsservice.dto.ParamDTO;
import br.gov.mg.bdmg.fsservice.exception.FileServiceException;
import br.gov.mg.bdmg.fsservice.exception.FileUtilException;
import br.gov.mg.bdmg.fsservice.model.ArquivoDado;
import br.gov.mg.bdmg.fsservice.service.FSService;
import br.gov.mg.bdmg.fsservice.util.StringUtil;
import io.swagger.annotations.ApiOperation;

@RestController
public class FSController {
	private final Logger LOGGER = LogManager.getLogger(this.getClass());

	@Autowired
	FSService fileService;

	@GetMapping("/download/{ID}")
	@ApiOperation("Download do arquivo")
	public ResponseEntity<?> downloadFile(@PathVariable("ID") String id,
			@RequestParam(name = "fromEncode", required = false) String fromEncode,
			@RequestParam(name = "toEncode", required = false) String toEncode,
			@RequestParam(name = "filename", required = false) String filenameSet,
			@RequestParam(name = "compactar", required = false, defaultValue = "N") String compactar)
			throws IOException, FileUtilException {

		LOGGER.info(new StringMapMessage().with("method", "downloadFile")
				.with("id", id)
				.with("fromEncode", StringUtil.ifNullEmpty(fromEncode))
				.with("toEncode", StringUtil.ifNullEmpty(toEncode))
				.with("filename", StringUtil.ifNullEmpty(filenameSet))
				.with("compactar", StringUtil.ifNullEmpty(compactar)));

		ArquivoDado arquivoDado = fileService.getById(id);

		Validate.notNull(arquivoDado, "id " + id + " nao encontrado!");

		byte[] bs = fileService.download(arquivoDado.getId(), fromEncode, toEncode);

		String nomArquivo = arquivoDado.getNomeOrigem();

		if ((filenameSet != null) && !filenameSet.isEmpty())
			nomArquivo = filenameSet;

		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		headers.add("Content-Disposition", "attachment; filename=" + nomArquivo + "");

		ByteArrayResource resource = new ByteArrayResource(bs);

		return ResponseEntity.ok().headers(headers).contentLength(bs.length)
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);

	}

	@GetMapping("/filename/{ID}")
	@ApiOperation("retorna nome do arquivo")
	public ResponseEntity<String> getNameFileById(@PathVariable("ID") String id) {
		LOGGER.info(new StringMapMessage().with("method", "getNameFileById").with("id", id));

		try {
			ArquivoDado arquivoDado = fileService.getById(id);

			Validate.notNull(arquivoDado, "id " + id + " nao encontrado!");

			return ResponseEntity.ok().header("filename", arquivoDado.getNomeOrigem())
					.body(arquivoDado.getNomeOrigem());
		} catch (Exception e) {
			LOGGER.error(new StringMapMessage().with("method", "getNameFileById").with("message", e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PostMapping("/upload")
	@ApiOperation("Realizar upload de arquivo")
	public Map<String, Object> uploadFile(
			@RequestParam(name = "categoria", required = false, defaultValue = "") String paramCategoria,
			@RequestParam(name = "descricao", required = false) String descricao,
			@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes
	// ,RequestHeader(value = "x-coduser", required = false, defaultValue = "")
	// String paramCodigoUsuario
	) throws FileServiceException, IOException {

		
		LOGGER.info(new StringMapMessage().with("method", "uploadFile").with("categoria", StringUtil.ifNullEmpty(paramCategoria))
				.with("descricao", StringUtil.ifNullEmpty(descricao)));

		ParamDTO paramTO = new ParamDTO();

		paramTO.setCodigoCategoria(
				((paramCategoria == null) || paramCategoria.isEmpty()) ? null : Integer.valueOf(paramCategoria));
		paramTO.setDescricao(descricao);
		// paramTO.setUsuario(paramCodigoUsuario);

		DadosDTO dadosDTO = new DadosDTO();
		byte[] bytes = IOUtils.toByteArray(file.getInputStream());
		dadosDTO.setInputStream(bytes);
		dadosDTO.setFileName(file.getOriginalFilename());

		paramTO.setDadosTO(dadosDTO);

		long id = fileService.uploadFile(paramTO);

		HashMap<String, Object> map = new HashMap<>();

		map.put("id", Long.valueOf(id));
		return map;
	}

	@GetMapping("/healthcheck")
	public String healthcheck() throws Exception {

		if (new File("/tmp/error.txt").exists())
			throw new Exception("/tmp/error.txt exists");

		return "OK";
	}

	@GetMapping("/union")
	@ApiOperation("Unir arquivos PDFs")
	public ResponseEntity<?> unionPDFs(@RequestParam("pdfs") String pdfs, @RequestParam("filename") String filename) {
		LOGGER.info(new StringMapMessage().with("method", "unionPDFs").with("pdfs", pdfs).with("filename", StringUtil.ifNullEmpty(filename)));
		try {
			ParamDTO paramTO = ParamDTO.builder().setPdf(pdfs.trim()).setFilename(filename);

			Long id = fileService.unionPDFFile(paramTO);

			return ResponseEntity.ok().header("cod_arq", id.toString()).body(id.toString());
		} catch (Exception e) {
			LOGGER.error(new StringMapMessage().with("method", "unionPDFs").with("message", e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/watermark")
	@ApiOperation("Marca d'agua em arquivos PDFs")
	public ResponseEntity<?> watermark(@RequestParam(name = "codArq") String arquivo,
			@RequestParam(name = "texto") String texto,
			@RequestParam(name = "filename", defaultValue = "") String filename) {
		LOGGER.info(new StringMapMessage().with("method", "watermark").with("texto", texto).with("filename", StringUtil.ifNullEmpty(filename)));
		try {
			Long codArq = Long.valueOf(arquivo);
			ParamDTO paramTO = ParamDTO.builder().setId(codArq).setTexto(texto).setFilename(filename)
					.setUsuario("rpsr");

			Long id = fileService.watermarkFile(paramTO);

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("cod_arq", id.toString());
			return ResponseEntity.ok().headers(responseHeaders).body(id.toString());
 
		} catch (Exception e) {
			LOGGER.error(new StringMapMessage().with("method", "watermark").with("message", e.getMessage()));

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}