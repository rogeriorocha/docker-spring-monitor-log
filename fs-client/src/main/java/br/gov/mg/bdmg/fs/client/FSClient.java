package br.gov.mg.bdmg.fs.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.gov.mg.bdmg.fs.dto.InfoResource;

@FeignClient(url="http://localhost:8080/", name = "viacep")
public interface FSClient {
 
    @GetMapping("api/v1/filename/{id}")
    InfoResource getData(@PathVariable("id") Long id);
}