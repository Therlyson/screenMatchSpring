package br.com.TherlysonDev.screenMatchSpring;

import br.com.TherlysonDev.screenMatchSpring.principal.Principal2;
import br.com.TherlysonDev.screenMatchSpring.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchSpringApplication implements CommandLineRunner{
	@Autowired
	private SerieRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal2 principal = new Principal2(repository);
		principal.exibeMenu();
	}
}
