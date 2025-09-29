package com.aluracursos.gutenberg_catalogue.repository;

import com.aluracursos.gutenberg_catalogue.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long>{

    Optional<Autor> findByNombreAndNacimientoFecha(String nombre, Integer nacimientoFecha);
//    List<Autor> findByNacimientoFechaLessThanEqualAndDecesoFechaIsNull(Integer anio)  ;
@Query("SELECT a FROM Autor a " +
        "WHERE a.nacimientoFecha <= :anio " +
        "AND (a.decesoFecha IS NULL OR a.decesoFecha > :anio)")
List<Autor> findAutoresVivosEnAnio(@Param("anio") Integer anio);



}
