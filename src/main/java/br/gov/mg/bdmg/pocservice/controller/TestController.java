package br.gov.mg.bdmg.pocservice.controller;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	private final Logger logger = LogManager.getLogger(this.getClass());

	@GetMapping("/")
	public String sayHello(@RequestParam(value = "name", defaultValue = "Guest") String name) {

		// info(String.format("id=[%s] firstname=[%d] surname=[%s]", user.getId(),
		// user.getFirstname(), user.getSurname()));

		logger.info(String.format("name=[%s] acao=[%s]", name, "XPTO"));

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
