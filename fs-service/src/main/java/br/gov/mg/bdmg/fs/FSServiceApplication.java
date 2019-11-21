package br.gov.mg.bdmg.fs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//EnablePrometheusMetrics 
@SpringBootApplication
//EnableConfigurationProperties(AppProperties.class)
//ComponentScan({"br.gov.mg.bdmg.fs.model", "br.gov.mg.bdmg.fs", "br.gov.mg.bdmg.fs.model.repository"})
//EnableAutoConfiguration 
//EnableJpaRepositories 
public class FSServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FSServiceApplication.class, args);
	}
	
	
}
