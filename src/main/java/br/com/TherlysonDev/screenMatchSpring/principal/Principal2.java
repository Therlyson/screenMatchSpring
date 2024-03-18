package br.com.TherlysonDev.screenMatchSpring.principal;

import br.com.TherlysonDev.screenMatchSpring.model.*;
import br.com.TherlysonDev.screenMatchSpring.repository.SerieRepository;
import br.com.TherlysonDev.screenMatchSpring.service.ConsumoApi;
import br.com.TherlysonDev.screenMatchSpring.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal2{
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apiKey=" + System.getenv("CHAVE_API_OMDB");
    private SerieRepository repository;
    List<Serie> series = new ArrayList<>();
    private Optional<Serie> serieBusca;

    public Principal2(SerieRepository repository) {
        this.repository = repository;
    }

    public void exibeMenu() {
        System.out.println("\n====================== BEM VINDO AO OMDB SÉRIES ======================");
        var opcao = -1;
        while(opcao != 0) {
            String menu = "\n -> 1 - Buscar uma série e adicionar ao Banco de dados" +
                    "\n -> 2 - Bucar todos os episódios de uma série" +
                    "\n -> 3 - Listar todas as séries" +
                    "\n -> 4 - Pesquisar uma série do banco de dados" +
                    "\n -> 5 - Pesquisar séries por nome do ator(a)" +
                    "\n -> 6 - Ver top 5 melhores episódios de uma série" +
                    "\n -> 7 - Buscar top 5 melhores séries do banco de dados" +
                    "\n -> 8 - Buscar séries por genêro" +
                    "\n -> 9 - Buscar séries até um número máximo de temporadas" +
                    "\n -> 10 - Buscar episódio por trecho" +
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
                case 4:
                    pesquisarSerieDoBanco();
                    break;
                case 5:
                    pesquisarSeriePorAtor();
                    break;
                case 6:
                    pesquisarMelhoresEpisodios();
                    break;
                case 7:
                    top5melhoresSeries();
                    break;
                case 8:
                    pesquisarSériesGenero();
                    break;
                case 9:
                    pesquisarPorNumTempMax();
                    break;
                case 10:
                    buscarEpisodioTrecho();
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
            Serie serie = new Serie(dados);
            repository.save(serie);
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
        listaDeSéries();
        System.out.print("\nDigite o nome da série para ver os episódios: ");
        var nomeSerie = scanner.nextLine();

        Optional<Serie> serie = series.stream().filter(s -> s.getTitle().toLowerCase()
                .contains(nomeSerie.toLowerCase())).findFirst();
        if(serie.isPresent()){
            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalSeasons(); i++) {
                var json = consumo.obterDadosJson(ENDERECO +
                        serieEncontrada.getTitle().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }

            List<Episodio> episodios = temporadas.stream().flatMap(t -> t.episodios().stream()
                    .map(e -> new Episodio(t.season(), e))).collect(Collectors.toList());
            for(Episodio ep: episodios){
                var json = consumo.obterDadosJson(ENDERECO + nomeSerie.replace(" ", "+")
                        + "&season=" + ep.getSeason() + "&episode=" + ep.getEpisode() + API_KEY);
                DadosEpisodio dadosEp = conversor.obterDados(json, DadosEpisodio.class);
                ep.setRuntime(dadosEp.runtime()); //adicionando a duração de cada ep
            }
            serieEncontrada.setEpisodios(episodios);
            repository.save(serieEncontrada);
            System.out.println("\n- Todas os episódios de " + serieEncontrada.getTitle() + ": ");
            for(Episodio ep: serieEncontrada.getEpisodios()){
                System.out.println(ep);
            }
        }else {
            System.out.println("Série não encontrada no banco de dados!");
        }
    }

    private void listaDeSéries(){
        System.out.println("\n- Séries pesquisadas por você: ");
        series = repository.findAll();
        series.stream().sorted(Comparator.comparing(Serie::getTitle))
                        .forEach(System.out::println);
    }

    private void pesquisarSerieDoBanco(){
        System.out.print("\nDigite o nome da série: ");
        String nomeSerie = scanner.nextLine();
        serieBusca = repository.findByTitleContainingIgnoreCase(nomeSerie);

        if(serieBusca.isPresent()){
            System.out.println("Série encontrada com sucesso: ");
            System.out.println(serieBusca.get());
        }else{
            System.out.println("Essa série não está presente no banco de dados!!");
        }
    }

    private void pesquisarSeriePorAtor(){
        System.out.print("\nDigite o nome do ator(a): ");
        String nomeAtor = scanner.nextLine();
        Optional<List<Serie>> serie = repository.findByActorsContainingIgnoreCase(nomeAtor);

        if(serie.isPresent()){
            System.out.println("Séries que " + nomeAtor + " atua: ");
            serie.get().forEach(System.out::println);
        }else{
            System.out.println("Não existe série no banco de dados com esse ator!");
        }
    }

    private void pesquisarMelhoresEpisodios(){
        pesquisarSerieDoBanco();
        if(serieBusca.isPresent()){
            List<Episodio> episodios = repository.top5melhoresEpisodios(serieBusca);
            System.out.println("\n-> Top 5 episódios melhores episódios: ");
            episodios.forEach(System.out::println);
        }else{
            System.out.println("Série não encontrada!");
        }
    }

    private void top5melhoresSeries(){
        List<Serie> series = repository.findTop5ByOrderByNoteImdbDesc();
        series.forEach(s -> System.out.println(s.getTitle() + ", Avaliação: " + s.getNoteImdb()));
    }

    private void pesquisarSériesGenero(){
        System.out.print("\nDigite o genêro para fazer a busca: ");
        String genero = scanner.nextLine();
        Categoria categoria = Categoria.buscaGenero(genero);
        List<Serie> series = repository.findByGenreContaining(categoria);
        series.forEach(System.out::println);
    }

    private void pesquisarPorNumTempMax(){
        System.out.print("\nDigite o número de temporadas para pesquisar: ");
        int numTemp = scanner.nextInt();
        System.out.print("Digite a nota da série: ");
        double nota = scanner.nextDouble();

        System.out.println("\nTodas as séries com no máximo " + numTemp + " temporadas e com nota acima de " +
                nota + ": ");
        List<Serie> series = repository.seriesPorTemporadaEAvaliacao(numTemp, nota);
        series.forEach(System.out::println);
    }

    private void buscarEpisodioTrecho(){
        System.out.println("\nDigite o trecho do episódio que você está procurando: ");
        String trecho = scanner.nextLine();
        List<Episodio> episodios = repository.episodiosPorTrecho(trecho);
        episodios.forEach(e -> System.out.println("Série: " + e.getSerie().getTitle() +
                ", Temporada: " + e.getSeason() + ", Episódio: " + e.getEpisode() +
                ", Título: " + e.getTitle() + ", Avaliação: " + e.getNoteImdb()));
    }
}