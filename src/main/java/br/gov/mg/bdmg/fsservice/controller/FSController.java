package br.gov.mg.bdmg.fsservice.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.gov.mg.bdmg.fsservice.dto.DadosDTO;
import br.gov.mg.bdmg.fsservice.dto.ParamDTO;
import br.gov.mg.bdmg.fsservice.exception.FileServiceException;
import br.gov.mg.bdmg.fsservice.model.ArquivoDado;
import br.gov.mg.bdmg.fsservice.service.FSService;
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
			throws IOException {

		LOGGER.info("Class: download Method: download - Codigo Arquivo: " + id);

		ArquivoDado arquivoDado = fileService.getById(id);

		Validate.notNull(arquivoDado, "id " + id + " nao encontrado!");

		byte[] bs = fileService.download(arquivoDado, fromEncode, toEncode);

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
	public ResponseEntity<String> getNameFileById(@PathVariable("ID") String id) {
		LOGGER.info("Class: FileRest Method: getNameFileById - Codigo Arquivo: " + id);
		try {
			ArquivoDado arquivoDado = fileService.getById(id);

			Validate.notNull(arquivoDado, "id " + id + " nao encontrado!");

			return ResponseEntity.ok().header("filename", arquivoDado.getNomeOrigem())
					.body(arquivoDado.getNomeOrigem());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PostMapping("/upload")
	public Map<String, Object> uploadFile(
			@RequestParam(name = "categoria", required = false, defaultValue = "") String paramCategoria,
			@RequestParam(name = "descricao", required = false) String descricao,
			@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
			@RequestHeader(value = "x-coduser", required = false, defaultValue = "") String paramCodigoUsuario)
			throws FileServiceException, IOException {

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
		;

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

	@GetMapping("/")
	public String sayHello(@RequestParam(value = "name", defaultValue = "Guest") String name) {

		// info(String.format("id=[%s] firstname=[%d] surname=[%s]", user.getId(),
		// user.getFirstname(), user.getSurname()));
		//
		//

		LOGGER.info(new StringMapMessage().with("id", 1).with("firstname", "ROGERIO"));

		LOGGER.info("RPSR");

		return "Hello " + name + "!!";
	}

	@GetMapping("/slowApi")
	public String timeConsumingAPI(@RequestParam(value = "delay", defaultValue = "0") Integer delay)
			throws InterruptedException {
		if (delay == 0) {
			Random random = new Random();
			delay = random.nextInt(10);
		}

		TimeUnit.SECONDS.sleep(delay);
		return "Result";
	}

}
