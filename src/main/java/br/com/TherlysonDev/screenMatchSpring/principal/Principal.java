package br.com.TherlysonDev.screenMatchSpring.principal;

import br.com.TherlysonDev.screenMatchSpring.controller.ControladorSerie;
import br.com.TherlysonDev.screenMatchSpring.model.DadosSerie;
import br.com.TherlysonDev.screenMatchSpring.model.DadosTemporada;
import br.com.TherlysonDev.screenMatchSpring.service.ConsumoApi;
import br.com.TherlysonDev.screenMatchSpring.service.ConverteDados;

import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados converte = new ConverteDados();
    private ControladorSerie controlador = new ControladorSerie();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String apiKey = "&apikey=d494adfa";

    public void exibirMenu(){
        System.out.println("====================== BEM VINDO AO OMDB FILMES ======================");
        System.out.print("-> Qual série deseja pesquisar: ");
        var nomeSerie = scanner.nextLine().replaceAll(" ", "+");

        String json = consumo.obterDadosJson(ENDERECO+nomeSerie+apiKey);
        DadosSerie dados = converte.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        String jsonTemp;
        for(int i = 1; i<=dados.totalSeasons(); i++){
            jsonTemp = consumo.obterDadosJson(ENDERECO + nomeSerie + "&season=" + i + apiKey);
            DadosTemporada dadosTemp = converte.obterDados(jsonTemp, DadosTemporada.class);
            controlador.adicionarTemporada(dadosTemp);
        }
        System.out.println();
        controlador.getTemporadas().forEach(System.out::println);
    }
}
