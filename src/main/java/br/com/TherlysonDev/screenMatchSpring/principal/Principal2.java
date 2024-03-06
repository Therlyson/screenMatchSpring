package br.com.TherlysonDev.screenMatchSpring.principal;

import br.com.TherlysonDev.screenMatchSpring.model.DadosSerie;
import br.com.TherlysonDev.screenMatchSpring.model.DadosTemporada;
import br.com.TherlysonDev.screenMatchSpring.model.Serie;
import br.com.TherlysonDev.screenMatchSpring.service.ConsumoApi;
import br.com.TherlysonDev.screenMatchSpring.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal2{
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apiKey=d494adfa";
    private List<DadosSerie> dadosSeries = new ArrayList<>();

    public void exibeMenu() {
        System.out.println("\n====================== BEM VINDO AO OMDB SÉRIES ======================");
        var opcao = -1;
        while(opcao != 0) {
            String menu = "\n -> 1 - Buscar série" +
                    "\n -> 2 - Bucar todos os episódios" +
                    "\n -> 3 - Listar todas as séries" + //implementado até aqui
                    "\n -> 4 - Ver as notas de cada temporada" +
                    "\n -> 5 - Buscar um episódio em especifico" +
                    "\n -> 6 - Ver avaliação final dos episódios" +
                    "\n -> 0 - SAIR";

            System.out.println(menu);
            System.out.print("Digite sua opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listaDeSéries();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        if(dados!=null){
            dadosSeries.add(dados);
            System.out.println(dados);
        }
    }

    private DadosSerie getDadosSerie() {
        System.out.print("\nDigite o nome da série para busca: ");
        var nomeSerie = scanner.nextLine();
        var json = consumo.obterDadosJson(ENDERECO +
                nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        if(dados.title() == null){
            System.out.println("ERRO, não foi encontrado série com esse nome!");
            return null;
        }
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();
        if(dadosSerie==null){return;}
        for (int i = 1; i <= dadosSerie.totalSeasons(); i++) {
            var json = consumo.obterDadosJson(ENDERECO +
                    dadosSerie.title().replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        System.out.println("\n- Todas as os episódios e temporadas: ");
        for(DadosTemporada temporada: temporadas){
            System.out.println(temporada.season() + "º TEMPORADA: ");
            temporada.episodios().forEach(System.out::println);
        }
    }

    private void listaDeSéries(){
        System.out.println("\n- Séries pesquisadas por você: ");
        List<Serie> series = new ArrayList<>();
        series = dadosSeries.stream().map(d -> new Serie(d))
                        .collect(Collectors.toList());
        series.stream().sorted(Comparator.comparing(Serie::getTitle))
                        .forEach(s -> System.out.println(s));
    }
}