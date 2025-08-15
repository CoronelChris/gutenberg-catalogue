package com.aluracursos.gutenberg_catalogue.service;

import com.aluracursos.gutenberg_catalogue.model.DatosLibro;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
        @JsonAlias("results")List<DatosLibro>resultados
) {

}
