package br.com.TherlysonDev.screenMatchSpring;

import br.com.TherlysonDev.screenMatchSpring.model.DadosEpisodio;
import br.com.TherlysonDev.screenMatchSpring.model.DadosTemporada;
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

		String jsonEp = consumApi.obterDadosJson("https://www.omdbapi.com/?t=dark&season=1&episode=1&apikey=d494adfa");
		DadosEpisodio dadosEp = converte.obterDados(jsonEp, DadosEpisodio.class);
		System.out.println(dadosEp);

		String jsonTemp = consumApi.obterDadosJson("https://www.omdbapi.com/?t=dark&season=1&apikey=d494adfa");
		DadosTemporada dadosTemp = converte.obterDados(jsonTemp, DadosTemporada.class);
		System.out.println(dadosTemp);
	}
}
