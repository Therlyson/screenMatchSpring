package br.com.TherlysonDev.screenMatchSpring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import br.com.TherlysonDev.screenMatchSpring.model.DadosSerie;
import br.com.TherlysonDev.screenMatchSpring.service.ConsumoApi;
import br.com.TherlysonDev.screenMatchSpring.service.ConverteDados;


@SpringBootApplication
public class ScreenMatchSpringApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumApi = new ConsumoApi();
		String json = consumApi.obterDadosJson("https://www.omdbapi.com/?t=dark&apikey=d494adfa");
		ConverteDados converte = new ConverteDados();
		DadosSerie dados = converte.obterDados(json, DadosSerie.class);
		System.out.println(dados);

	}
}
