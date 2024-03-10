package br.com.TherlysonDev.screenMatchSpring.repository;

import br.com.TherlysonDev.screenMatchSpring.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
