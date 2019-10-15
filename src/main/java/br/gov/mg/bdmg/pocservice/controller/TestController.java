package br.gov.mg.bdmg.pocservice.controller;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String sayHello(@RequestParam(value = "name", defaultValue = "Guest") String name) {
        return "Hello " + name + "!!";
    }

    @GetMapping("/slowApi")
    public String timeConsumingAPI(@RequestParam(value = "delay", defaultValue = "0") Integer delay) throws InterruptedException {
        if(delay == 0) {
            Random random = new Random();
            delay = random.nextInt(10);
        }

        TimeUnit.SECONDS.sleep(delay);
        return "Result";
    }	

}
