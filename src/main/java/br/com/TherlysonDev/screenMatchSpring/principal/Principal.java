//package br.com.TherlysonDev.screenMatchSpring.principal;
//
//import br.com.TherlysonDev.screenMatchSpring.model.*;
//import br.com.TherlysonDev.screenMatchSpring.service.ConsumoApi;
//import br.com.TherlysonDev.screenMatchSpring.service.ConverteDados;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class Principal {
//    private Scanner scanner = new Scanner(System.in);
//    private ConsumoApi consumo = new ConsumoApi();
//    private ConverteDados converte = new ConverteDados();
//    private List<DadosTemporada> controladorSerie = new ArrayList<>();
//    private final String ENDERECO = "https://www.omdbapi.com/?t=";
//    private final String API_KEY = "&apiKey=d494adfa";
//
//    public void exibirMenu(){
//        System.out.println("====================== BEM VINDO AO OMDB FILMES ======================");
//
//        System.out.print("-> Qual série deseja pesquisar: ");
//        var nomeSerie = scanner.nextLine().replaceAll(" ", "+");
//        String json = consumo.obterDadosJson(ENDERECO+nomeSerie+API_KEY);
//        DadosSerie dados = converte.obterDados(json, DadosSerie.class);
//        System.out.println(dados);
//
//        String jsonTemp;
//        for(int i = 1; i<=dados.totalSeasons(); i++){
//            jsonTemp = consumo.obterDadosJson(ENDERECO + nomeSerie + "&season=" + i + API_KEY);
//            DadosTemporada dadosTemp = converte.obterDados(jsonTemp, DadosTemporada.class);
//            controladorSerie.add(dadosTemp);
//        }
//
//        System.out.println("\n- Todas as os episódios e temporadas: ");
//        for(DadosTemporada temporada: controladorSerie){
//            System.out.println(temporada.season() + "º TEMPORADA: ");
//            temporada.episodios().forEach(System.out::println);
//        }
//
//        List<Episodio> episodios = controladorSerie.stream()
//                .flatMap(t -> t.episodios().stream() //flatMap junta os episódios de todas as temporadas
//                        .map(d -> new Episodio(t.season(), d))) //transformando cada dadoEpisodio em um episodio
//                .collect(Collectors.toList());
//        for(Episodio ep: episodios){
//            json = consumo.obterDadosJson(ENDERECO + nomeSerie + "&season=" + ep.getSeason()
//                    + "&episode=" + ep.getEpisode() + API_KEY);
//            DadosEpisodio dadosEp = converte.obterDados(json, DadosEpisodio.class);
//            ep.setRuntime(dadosEp.runtime()); //adicionando a duração de cada ep
//        }
//
//        System.out.println("\n- Top 5 melhores episódios de " + nomeSerie + ": ");
//        episodios.stream().sorted(Comparator.comparing(Episodio::getNoteImdb).reversed()) //compara e retorna em ordem descrescente por nota
//            .limit(5)
//            .forEach(System.out::println);
//
//        System.out.println("\n- Avaliações de cada temporada: ");
//        Map<Integer, Double> avaliaçoesPorTemporada = episodios.stream()
//                .filter(e -> e.getNoteImdb() > 0.0)
//                .collect(Collectors.groupingBy
//                        (Episodio::getSeason, Collectors.averagingDouble(Episodio::getNoteImdb))); //faz a média de todos os ep dessa temporada
//        for(int i =0; i< avaliaçoesPorTemporada.size(); i++){
//            int temp = i+1;
//            System.out.println("Temporada: " + temp + ", Nota: " +
//                    String.format("%.2f", avaliaçoesPorTemporada.get(temp)));
//        }
//
//        System.out.print("\nDigite o título ou um trecho do titulo que você está procurando: ");
//        var titulo = scanner.nextLine();
//        Optional<Episodio> epEncontrado = episodios.stream().filter(e -> e.getTitle().toUpperCase().contains(titulo.toUpperCase()))
//                .findFirst(); //pega o primeiro.
//        if(epEncontrado.isPresent()){
//            System.out.println("-> " + epEncontrado.get());
//        }else{
//            System.out.println("Não foi encontrada nenhum episódio com essa palavra.");
//        }
//
//        System.out.println("\n- Avaliação final de todos os episódios: ");
//        DoubleSummaryStatistics est = episodios.stream()  //vai devolver um conjunto com algumas estatisticas importantes
//                .filter(e -> e.getNoteImdb() > 0.0)
//                .collect(Collectors.summarizingDouble(Episodio::getNoteImdb));
//        System.out.println("Total de episódios avaliados: " + est.getCount());
//        System.out.println("Média das avaliações: " + est.getAverage());
//        System.out.println("Menor avaliação de um episódio: " + est.getMin());
//        System.out.println("Maior avaliação de um episódio: " + est.getMax());
//    }
//}