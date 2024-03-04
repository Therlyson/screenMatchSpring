package br.com.TherlysonDev.screenMatchSpring.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Episodio {
    private Integer season;
    private String title;
    private Integer episode;
    private String runtime;
    private Double noteImdb;
    private LocalDate released;

    public Episodio(Integer season, DadosEpisodio dadosEpisodio) {
        this.season = season;
        this.title = dadosEpisodio.title();
        this.episode = dadosEpisodio.episode();
        this.runtime = dadosEpisodio.runtime();
        try{
            this.noteImdb = Double.valueOf(dadosEpisodio.noteImdb());
            this.released = LocalDate.parse(dadosEpisodio.released());
        }catch (NumberFormatException | NullPointerException e){
            this.noteImdb = 0.0;
            this.released = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public String getTitle() {
        return title;
    }

    public Integer getEpisode() {
        return episode;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public Double getNoteImdb() {
        return noteImdb;
    }

    public LocalDate getReleased() {
        return released;
    }

    @Override
    public String toString() {
        return "Episódio: " + this.episode + ", Temporada: " + this.season + ", Titulo: " + this.title +
                ", Duração: " + this.runtime + ", Nota do imdb: " + this.noteImdb
                + ", Data de lançamento: " +
                (released != null ? released.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null);
    }
}
