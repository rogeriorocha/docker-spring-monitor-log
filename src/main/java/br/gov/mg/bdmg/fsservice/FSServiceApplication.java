package br.gov.mg.bdmg.fsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//EnablePrometheusMetrics 
@SpringBootApplication
//EnableConfigurationProperties(AppProperties.class)
public class FSServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FSServiceApplication.class, args);
	}
	
	
}
