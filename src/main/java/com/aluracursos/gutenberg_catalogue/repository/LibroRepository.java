package com.aluracursos.gutenberg_catalogue.repository;

import com.aluracursos.gutenberg_catalogue.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
