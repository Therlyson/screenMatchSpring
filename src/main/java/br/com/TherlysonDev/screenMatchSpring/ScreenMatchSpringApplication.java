package br.com.TherlysonDev.screenMatchSpring;

import br.com.TherlysonDev.screenMatchSpring.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchSpringApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibirMenu();
	}
}
