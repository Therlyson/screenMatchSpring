package br.com.TherlysonDev.screenMatchSpring.controller;

import br.com.TherlysonDev.screenMatchSpring.model.DadosTemporada;

import java.util.ArrayList;
import java.util.List;

public class ControladorSerie {
    List<DadosTemporada> temporadas;

    public ControladorSerie() {
        this.temporadas = new ArrayList<>();
    }

    public List<DadosTemporada> getTemporadas() {
        return temporadas;
    }

    public void adicionarTemporada(DadosTemporada temporada){
        this.temporadas.add(temporada);
    }
}
