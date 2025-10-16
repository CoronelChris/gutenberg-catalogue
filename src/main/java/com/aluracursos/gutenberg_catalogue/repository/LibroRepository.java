package com.aluracursos.gutenberg_catalogue.repository;

import com.aluracursos.gutenberg_catalogue.model.Lenguaje;
import com.aluracursos.gutenberg_catalogue.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {


    List<Libro>findByIdioma (Lenguaje lenguaje);
    Optional<Libro>findByTituloAndAutorNombre(String titulo, String nombreAutor);
}
