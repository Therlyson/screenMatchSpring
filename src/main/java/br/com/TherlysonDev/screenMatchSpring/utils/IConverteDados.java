package br.com.TherlysonDev.screenMatchSpring.utils;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}