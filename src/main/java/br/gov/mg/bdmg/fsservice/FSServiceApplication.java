package br.gov.mg.bdmg.fsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

//EnablePrometheusMetrics 
@SpringBootApplication

public class FSServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FSServiceApplication.class, args);
	}
	
	
}
