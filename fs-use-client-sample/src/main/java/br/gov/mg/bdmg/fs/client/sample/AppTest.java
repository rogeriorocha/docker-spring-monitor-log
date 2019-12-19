package br.gov.mg.bdmg.fs.client.sample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import br.gov.mg.bdmg.fs.client.FSClient;
import br.gov.mg.bdmg.fs.dto.InfoResource;

@SpringBootApplication
@EnableFeignClients
@ComponentScan({"br.gov.mg.bdmg.fs.client"})
public class AppTest {

	public static void main(String[] args) {
		SpringApplication.run(AppTest.class, args);
	}

	@Bean
	public CommandLineRunner run(FSClient client) {
		return args -> {

				InfoResource dto = client.getData(Long.valueOf(4490477));

				System.out.println(dto.toString());

		};
	}

}
