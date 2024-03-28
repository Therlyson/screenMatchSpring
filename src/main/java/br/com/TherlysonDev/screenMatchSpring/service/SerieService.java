package br.com.TherlysonDev.screenMatchSpring.service;

import br.com.TherlysonDev.screenMatchSpring.DTO.EpisodioDTO;
import br.com.TherlysonDev.screenMatchSpring.DTO.SerieDTO;
import br.com.TherlysonDev.screenMatchSpring.model.Categoria;
import br.com.TherlysonDev.screenMatchSpring.model.Serie;
import br.com.TherlysonDev.screenMatchSpring.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obterTodasAsSeries(){
        return converteSerie(repository.findAll());
    }

    public List<SerieDTO> obterTop5series(){
        return converteSerie(repository.findTop5ByOrderByNoteImdbDesc());
    }

    public List<SerieDTO> ultimosLancamentos() {
        return converteSerie(repository.top5seriesMaisRecentes());
    }

    public SerieDTO obterSeriePorId(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitle(), s.getGenre(), s.getNoteImdb(), s.getTotalSeasons(),
                    s.getActors(), s.getPlot(), s.getPoster());
        }
        return null;
    }

    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getSeason(), e.getTitle(), e.getEpisode(), e.getRuntime(), e.getNoteImdb()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obterTemporadaEspecifica(Long id, Integer numeroTemp) {
        Optional<Serie> serie = repository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .filter(e -> e.getSeason() == numeroTemp)
                    .map(e -> new EpisodioDTO(e.getSeason(), e.getTitle(), e.getEpisode(), e.getRuntime(), e.getNoteImdb()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<SerieDTO> obterSeriesPorCategoria(String categoriaEscolhida) {
       Categoria categoria = Categoria.buscaGenero(categoriaEscolhida);
       if(categoria != null){
           return converteSerie(repository.seriesPorCategoria(categoria));
       }
       return null;
    }


    private List<SerieDTO> converteSerie(List<Serie> series){
        return series.stream().map(s -> new SerieDTO(
                        s.getId(), s.getTitle(), s.getGenre(), s.getNoteImdb(), s.getTotalSeasons(),
                        s.getActors(), s.getPlot(), s.getPoster()))
                .collect(Collectors.toList());
    }

    public List<EpisodioDTO> obterTopEpisodios(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return repository.top5melhoresEpisodios(s).stream()
                    .map(e -> new EpisodioDTO(e.getSeason(), e.getTitle(), e.getEpisode(), e.getRuntime(), e.getNoteImdb()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
