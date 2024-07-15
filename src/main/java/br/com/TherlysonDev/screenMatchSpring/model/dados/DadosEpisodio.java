package br.com.TherlysonDev.screenMatchSpring.model.dados;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(@JsonAlias("Title") String title, @JsonAlias("Episode") Integer episode,
                            @JsonAlias("Runtime") String runtime,
                            @JsonAlias("imdbRating") String noteImdb, @JsonAlias("Released") String released) {
    @Override
    public String toString() {
        return "Episódio: " + episode + ", Titulo: " + title +
                ", Duração: " + runtime + ", Nota do imdb: " + noteImdb
                + ", Data de lançamento: " + released;
    }
}
