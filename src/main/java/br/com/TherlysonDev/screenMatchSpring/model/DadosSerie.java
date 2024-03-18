package br.com.TherlysonDev.screenMatchSpring.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String title,@JsonAlias("Genre") String genre,
                         @JsonAlias("imdbRating") Double noteImdb,
                         @JsonAlias("totalSeasons") Integer totalSeasons,@JsonAlias("Actors") String actors,
                         @JsonAlias("Plot") String plot, @JsonAlias("Poster") String poster) {
    @Override
    public String toString() {
        return "Titulo: " + title + "\nGênero: " + Categoria.fromPortugues(Serie.generosList(genre)) +
                "\nNota do imdb: " + noteImdb + "\nTotal de temporadas: " +
                totalSeasons + "\nAtores: " + actors + "\nSinopese: " + plot
                + "\nImagem: " + poster;
    }
}