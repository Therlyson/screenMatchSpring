package br.com.TherlysonDev.screenMatchSpring.service;

import br.com.TherlysonDev.screenMatchSpring.DTO.SerieDTO;
import br.com.TherlysonDev.screenMatchSpring.model.Serie;
import br.com.TherlysonDev.screenMatchSpring.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    private List<SerieDTO> converteSerie(List<Serie> series){
        return series.stream().map(s -> new SerieDTO(
                        s.getId(), s.getTitle(), s.getGenre(), s.getNoteImdb(), s.getTotalSeasons(),
                        s.getActors(), s.getPlot(), s.getPoster()))
                .collect(Collectors.toList());
    }
}
