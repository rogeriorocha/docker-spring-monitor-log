package br.gov.mg.bdmg.fs.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.gov.mg.bdmg.fs.dto.DadosDTO;
import br.gov.mg.bdmg.fs.dto.ExpurgoResource;
import br.gov.mg.bdmg.fs.dto.InfoResource;
import br.gov.mg.bdmg.fs.dto.ParamDTO;
import br.gov.mg.bdmg.fs.dto.UnionResource;
import br.gov.mg.bdmg.fs.dto.UploadResponseResource;
import br.gov.mg.bdmg.fs.exception.FileServiceException;
import br.gov.mg.bdmg.fs.exception.FileUtilException;
import br.gov.mg.bdmg.fs.model.ArquivoDado;
import br.gov.mg.bdmg.fs.service.FSService;
import br.gov.mg.bdmg.fs.util.StringUtil;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/fs")
public class FSController {
	private final Logger LOGGER = LogManager.getLogger(this.getClass());

	@Autowired
	FSService fileService;
 
	@GetMapping("/stress")
	@ApiOperation("stress")
	public String stress() {
		double x = 0.0001;
		for (int i = 0; i <= 1000000; i++) {
		    x += Math.sqrt(x);
		  }
		  return "OK!";		
	}

	@GetMapping("/")
	@ApiOperation("test")
	public String test() {
		try {
			return "Hello " + new Date() + " " + InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "Hello " + new Date();
		}
	}

	@GetMapping("/download/{ID}")
	@ApiOperation("Download do arquivo")
	public ResponseEntity<?> downloadFile(@PathVariable("ID") String id,
			@RequestParam(name = "fromEncode", required = false) String fromEncode,
			@RequestParam(name = "toEncode", required = false) String toEncode,
			@RequestParam(name = "filename", required = false) String filenameSet,
			@RequestParam(name = "compactar", required = false, defaultValue = "N") String compactar)
			throws IOException, FileUtilException {

		LOGGER.info(new StringMapMessage().with("method", "downloadFile").with("id", id)
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

	@GetMapping("/info/{ID}")
	@ApiOperation("retorna nome do arquivo")
	public ResponseEntity<InfoResource> getNameFileById(@PathVariable("ID") String id) {
		LOGGER.info(new StringMapMessage().with("method", "getNameFileById").with("id", id));

		InfoResource e = new InfoResource();
		try {
			ArquivoDado arquivoDado = fileService.getById(id);

			Validate.notNull(arquivoDado, "id " + id + " nao encontrado!");

			e.setId(arquivoDado.getId());
			e.setHash(arquivoDado.getHash());
			e.setAtivo(arquivoDado.getAtivo());
			e.setFilename(arquivoDado.getNomeOrigem());

			return ResponseEntity.ok().header("filename", arquivoDado.getNomeOrigem()).body(e);
		} catch (Exception er) {
			LOGGER.error(new StringMapMessage().with("method", "getNameFileById").with("message", er.getMessage()));
			e.setErrorMessage(er.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
		}
	}

	@PostMapping("/upload")
	@ApiOperation("Realizar upload de arquivo")
	public ResponseEntity<UploadResponseResource> uploadFile(
			@RequestParam(name = "categoria", required = false, defaultValue = "") String paramCategoria,
			@RequestParam(name = "descricao", required = false) String descricao,
			@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
			@RequestHeader(value = "x-coduser", required = false, defaultValue = "") String paramCodigoUsuario)
			throws FileServiceException, IOException {

		UploadResponseResource r = new UploadResponseResource();
		try {

			LOGGER.info(new StringMapMessage().with("method", "uploadFile")
					.with("categoria", StringUtil.ifNullEmpty(paramCategoria))
					.with("descricao", StringUtil.ifNullEmpty(descricao)));

			ParamDTO paramTO = new ParamDTO();

			paramTO.setCodigoCategoria(
					((paramCategoria == null) || paramCategoria.isEmpty()) ? null : Integer.valueOf(paramCategoria));
			paramTO.setDescricao(descricao);
			paramTO.setUsuario(paramCodigoUsuario);

			DadosDTO dadosDTO = new DadosDTO();
			byte[] bytes = IOUtils.toByteArray(file.getInputStream());
			dadosDTO.setInputStream(bytes);
			dadosDTO.setFileName(file.getOriginalFilename());

			paramTO.setDadosTO(dadosDTO);

			long id = fileService.uploadFile(paramTO);

			r.setId(id);

			return ResponseEntity.ok().body(r);
		} catch (Exception e) {
			LOGGER.error(new StringMapMessage().with("method", "getNameFileById").with("message", e.getMessage()));
			r.setErrorMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(r);
		}

	}

	@GetMapping("/healthcheck")
	public String healthcheck() throws Exception {
		fileService.healthcheck();

		return "OK";
	}

	@PostMapping("/union")
	@ApiOperation("Unir arquivos PDFs")
	public ResponseEntity<UnionResource> unionPDFs(@RequestParam("pdfs") String pdfs,
			@RequestParam("filename") String filename) {
		LOGGER.info(new StringMapMessage().with("method", "unionPDFs").with("pdfs", pdfs).with("filename",
				StringUtil.ifNullEmpty(filename)));
		UnionResource r = new UnionResource();
		try {
			ParamDTO paramTO = ParamDTO.builder().setPdf(pdfs.trim()).setFilename(filename);

			Long id = fileService.unionPDFFile(paramTO);

			r.setId(id);

			return ResponseEntity.ok().header("cod_arq", id.toString()).body(r);
		} catch (Exception e) {

			LOGGER.error(new StringMapMessage().with("method", "unionPDFs").with("message", e.getMessage()));
			r.setErrorMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(r);
		}
	}

	@DeleteMapping("/expurgar")
	@ApiOperation("Expurga arquivos marcados")
	public ResponseEntity<ExpurgoResource> expurgar() {

		ExpurgoResource r = new ExpurgoResource();
		try {
			r.setQtdeDeletada(fileService.expurgar());

			return ResponseEntity.ok().body(r);

		} catch (Exception e) {
			LOGGER.error(new StringMapMessage().with("method", "expurgar").with("message", e.getMessage()));
			r.setErrorMessage(e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(r);
		}

	}

	@PostMapping("/watermark")
	@ApiOperation("Marca d'agua em arquivos PDFs")
	public ResponseEntity<?> watermark(@RequestParam(name = "codArq") String arquivo,
			@RequestParam(name = "texto") String texto,
			@RequestParam(name = "filename", defaultValue = "") String filename) {
		LOGGER.info(new StringMapMessage().with("method", "watermark").with("texto", texto).with("filename",
				StringUtil.ifNullEmpty(filename)));
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