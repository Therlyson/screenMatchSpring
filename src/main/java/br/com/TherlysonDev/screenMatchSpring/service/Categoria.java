package br.com.TherlysonDev.screenMatchSpring.service;

import java.util.ArrayList;
import java.util.List;

public enum Categoria {
    ROMANCE("Romance"),
    TERROR("Horror"),
    ACAO("Action"),
    COMEDIA("Comedy"),
    CRIME("Crime"),
    DRAMA("Drama"),
    MISTERIO("Mystery"),
    FANTASIA("Fantasy");

    private String categoriaOmdb;

    Categoria(String categoriaOmdb){
        this.categoriaOmdb = categoriaOmdb;
    }

    public static List<Categoria> fromPortugues(List<String> generos){
        List<Categoria> generos2 = new ArrayList<>();
        for(Categoria categoria: Categoria.values()){
            for(String g: generos){
                if(categoria.categoriaOmdb.equalsIgnoreCase(g)){
                    generos2.add(categoria);
                }
            }
        }
        if(!generos2.isEmpty()){
            return generos2;
        }
        throw new RuntimeException("Erro ao encontrar categoria!");
    }
}
