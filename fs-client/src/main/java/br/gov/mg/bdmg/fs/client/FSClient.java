package br.gov.mg.bdmg.fs.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import br.gov.mg.bdmg.fs.dto.InfoResource;
import br.gov.mg.bdmg.fs.dto.UploadResponseResource;

@FeignClient(url = "http://lxdocker:8080/", name = "fs", path = "/api/")
public interface FSClient {

	@GetMapping("/v1/info/{id}")
	InfoResource getData(@PathVariable("id") Long id);

	@RequestMapping(value = "/v1/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
	UploadResponseResource upload(@RequestPart MultipartFile file,
			@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "categoria", required = false) String categoria);

}