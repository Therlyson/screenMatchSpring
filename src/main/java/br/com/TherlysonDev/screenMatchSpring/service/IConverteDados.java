package br.com.TherlysonDev.screenMatchSpring.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}