package br.com.TherlysonDev.screenMatchSpring.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(@JsonAlias("Title") String title,@JsonAlias("Episode") String episode,
                            @JsonAlias("Runtime") String runtime,
                            @JsonAlias("imdbRating") String noteImdb) {
}
