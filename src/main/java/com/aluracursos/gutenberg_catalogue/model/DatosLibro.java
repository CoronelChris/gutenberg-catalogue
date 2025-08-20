package com.aluracursos.gutenberg_catalogue.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("authors") List<DatosAutor> autor,
        @JsonAlias("download_count") Double numeroDescargas
) {
}
