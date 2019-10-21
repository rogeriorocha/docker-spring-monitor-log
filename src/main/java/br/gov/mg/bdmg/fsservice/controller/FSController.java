package br.gov.mg.bdmg.fsservice.controller;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.mg.bdmg.fsservice.model.ArquivoDado;
import br.gov.mg.bdmg.fsservice.service.FSService;

@RestController
public class FSController {
	private final Logger LOGGER = LogManager.getLogger(this.getClass());

	
	@Autowired
	FSService fileService;
			
	
	@GetMapping("/download/{ID}")
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
