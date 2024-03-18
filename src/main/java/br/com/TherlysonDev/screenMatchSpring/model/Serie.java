package br.com.TherlysonDev.screenMatchSpring.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private String genre;
    private Double noteImdb;
    private Integer totalSeasons;
    private String actors;
    private String plot;
    private String poster;
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios;

    public Serie(DadosSerie dadosSerie){
        this.title = dadosSerie.title();
        this.genre = Categoria.fromPortugues(generosList(dadosSerie.genre())); //retorna a tradução dos genêros em português
        this.noteImdb = dadosSerie.noteImdb();
        this.totalSeasons = dadosSerie.totalSeasons();
        this.actors = dadosSerie.actors();
        this.plot = dadosSerie.plot(); //adicionar o gpt para traduzir
        this.poster = dadosSerie.poster();
    }

    public Serie(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this)); //bidirecionamento
        this.episodios = episodios;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
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

    public static List<String> generosList(String genre){
        String[] separar = genre.split(",");
        List<String> todosGeneros = new ArrayList<>();
        for(String s: separar){
            todosGeneros.add(s.trim());
        }
        return todosGeneros;
    }

    @Override
    public String toString() {
        return "Titulo: " + title + ", Gênero: " + genre +
                ", Nota do imdb: " + noteImdb + ", Total de temporadas: " +
                totalSeasons + ", Atores: " + actors + ", Sinopese: " + plot
                + ", Imagem: " + poster  + ", Episódios: " + episodios;
    }
}
