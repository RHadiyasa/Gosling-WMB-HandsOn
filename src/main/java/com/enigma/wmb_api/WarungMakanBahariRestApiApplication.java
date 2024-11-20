package com.enigma.wmb_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@SpringBootApplication
public class WarungMakanBahariRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarungMakanBahariRestApiApplication.class, args);
	}
//
//	@GetMapping("/hello")
//	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
//		return String.format("Hello %s!", name);
//	}
}
