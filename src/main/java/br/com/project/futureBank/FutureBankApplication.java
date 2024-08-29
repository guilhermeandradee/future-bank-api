package br.com.project.futureBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FutureBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(FutureBankApplication.class, args);
	}

}
