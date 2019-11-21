package br.gov.mg.bdmg.fs.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import br.gov.mg.bdmg.fs.dto.InfoResource;

@SpringBootApplication
@EnableFeignClients
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public CommandLineRunner run(FSClient client) {
		return args -> {

				InfoResource dto = client.getData(Long.valueOf(4490477));

				System.out.println(dto.toString());

		};
	}

}
