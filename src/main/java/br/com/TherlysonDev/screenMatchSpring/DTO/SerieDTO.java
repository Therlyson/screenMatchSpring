package br.com.TherlysonDev.screenMatchSpring.DTO;

public record SerieDTO(Long id, String title, String genre, Double noteImdb,
                       Integer totalSeasons, String actors, String plot, String poster) {
}
