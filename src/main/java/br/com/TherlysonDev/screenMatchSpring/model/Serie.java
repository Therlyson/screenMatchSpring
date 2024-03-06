package br.com.TherlysonDev.screenMatchSpring.model;

import br.com.TherlysonDev.screenMatchSpring.service.Categoria;
import br.com.TherlysonDev.screenMatchSpring.service.ConsultaGpt;

import java.util.ArrayList;
import java.util.List;

public class Serie {
    private String title;
    private List<Categoria> genre;
    private Double noteImdb;
    private Integer totalSeasons;
    private String actors;
    private String plot;
    private String poster;

    public Serie(DadosSerie dadosSerie){
        this.title = dadosSerie.title();
        this.genre = Categoria.fromPortugues(generosList(dadosSerie.genre())); //retorna uma lista com os generos em portugues
        this.noteImdb = dadosSerie.noteImdb();
        this.totalSeasons = dadosSerie.totalSeasons();
        this.actors = dadosSerie.actors();
        this.plot = dadosSerie.plot();
        this.plot = ConsultaGpt.obterTraducao(dadosSerie.plot()).trim();
        this.poster = dadosSerie.poster();
    }

    public List<String> generosList(String genre){
        String[] separar = genre.split(",");
        List<String> todosGeneros = new ArrayList<>();
        for(String s: separar){
            todosGeneros.add(s.trim());
        }
        return todosGeneros;
    }

    public String getTitle() {
        return title;
    }

    public List<Categoria> getGenre() {
        return genre;
    }

    public Double getNoteImdb() {
        return noteImdb;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getPoster() {
        return poster;
    }

    @Override
    public String toString() {
        return "Titulo: " + title + "\nGênero: " + genre +
                "\nNota do imdb: " + noteImdb + "\nTotal de temporadas: " +
                totalSeasons + "\nAtores: " + actors + "\nSinopese: " + plot
                + "\nImagem: " + poster;
    }
}
