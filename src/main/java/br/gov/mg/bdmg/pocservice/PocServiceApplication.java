package br.gov.mg.bdmg.pocservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//EnablePrometheusMetrics 
@SpringBootApplication
public class PocServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocServiceApplication.class, args);
	}

}
