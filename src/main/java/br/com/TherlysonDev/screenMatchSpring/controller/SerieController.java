package br.com.TherlysonDev.screenMatchSpring.controller;

import br.com.TherlysonDev.screenMatchSpring.DTO.EpisodioDTO;
import br.com.TherlysonDev.screenMatchSpring.DTO.SerieDTO;
import br.com.TherlysonDev.screenMatchSpring.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {
    @Autowired
    private SerieService service;

    @GetMapping
    public List<SerieDTO> obterSeries(){
        return service.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5series(){
        return service.obterTop5series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> ultimosLancamentos(){
        return service.ultimosLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterSeriePorId(@PathVariable Long id){
        return service.obterSeriePorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id){
        return service.obterTodasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numeroTemp}")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id, @PathVariable Integer numeroTemp){
        return service.obterTemporadaEspecifica(id, numeroTemp);
    }

    @GetMapping("/categoria/{categoriaEscolhida}")
    public List<SerieDTO> obterSeriesPorCategoria(@PathVariable String categoriaEscolhida){
        return service.obterSeriesPorCategoria(categoriaEscolhida);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> obterTopEpisodios(@PathVariable Long id){
        return service.obterTopEpisodios(id);
    }
}
