package br.com.TherlysonDev.screenMatchSpring.model;

import java.util.List;

public enum Categoria {
    ROMANCE("Romance", "Romance"),
    TERROR("Horror", "Terror"),
    ACAO("Action", "Ação"),
    COMEDIA("Comedy", "Comédia"),
    CRIME("Crime", "Crime"),
    DRAMA("Drama", "Drama"),
    MISTERIO("Mystery", "Mistério"),
    FANTASIA("Fantasy", "Fantasia"),
    AVENTURA("Adventure", "Aventura");

    private String categoriaOmdb;
    private String categoriaPortugues;

    Categoria(String categoriaOmdb, String categoriaPortugues){
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPortugues = categoriaPortugues;
    }

    public static String fromPortugues(List<String> generos){
        StringBuilder generosTest = new StringBuilder();
        for(Categoria categoria: Categoria.values()){
            for(String g: generos){
                if(categoria.categoriaOmdb.equalsIgnoreCase(g)){
                    generosTest.append(categoria);
                    generosTest.append(", ");
                }
            }
        }
        generosTest.delete(generosTest.length() - 2, generosTest.length());
        if(!generosTest.isEmpty()){
            return generosTest.toString();
        }
        throw new RuntimeException("Erro ao encontrar categoria!");
    }

    public static Categoria buscaGenero(String nomeGenero){
        for(Categoria categoria: Categoria.values()){
            if(categoria.categoriaPortugues.equalsIgnoreCase(nomeGenero)){
                return categoria;
            }
        }
        throw new RuntimeException("Não foi encontrado nenhum genêro com esse nome!");
    }
}