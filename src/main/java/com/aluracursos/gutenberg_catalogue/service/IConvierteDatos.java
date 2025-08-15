package com.aluracursos.gutenberg_catalogue.service;

public interface IConvierteDatos {
    <T>T obtenerDatos(String json, Class <T>clase);
}

