package br.com.TherlysonDev.screenMatchSpring.repository;

import br.com.TherlysonDev.screenMatchSpring.model.Categoria;
import br.com.TherlysonDev.screenMatchSpring.model.Episodio;
import br.com.TherlysonDev.screenMatchSpring.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTitleContainingIgnoreCase(String nomeSerie);
    Optional<List<Serie>> findByActorsContainingIgnoreCase(String nomeAtor);
    List<Serie> findTop5ByOrderByNoteImdbDesc();
    List<Serie> findByGenreContaining(Categoria categoria);
    List<Serie> findByTotalSeasonsLessThanEqualAndNoteImdbGreaterThanEqual(int numeroTemp, double nota);

    //utilizando JPQL
    @Query("select s from Serie s WHERE s.totalSeasons <= :temporada AND s.noteImdb >= :avaliacao")
    List<Serie> seriesPorTemporadaEAvaliacao(int temporada, double avaliacao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.title ILIKE %:trecho%")
    List<Episodio> episodiosPorTrecho(String trecho);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.noteImdb DESC LIMIT 5")
    List<Episodio> top5melhoresEpisodios(Optional<Serie> serie);
}
