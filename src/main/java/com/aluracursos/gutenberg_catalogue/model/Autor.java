package com.aluracursos.gutenberg_catalogue.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Column(unique = true)
    private String nombre;
    private Integer nacimientoFecha;
    private Integer decesoFecha;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Libro>libros = new ArrayList<>();

    public Autor() {}

    public Autor(String nombre, Integer nacimientoFecha, Integer decesoFecha) {
        this.nombre = nombre;
        this.nacimientoFecha = nacimientoFecha;
        this.decesoFecha = decesoFecha;
    }
    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNacimientoFecha() {
        return nacimientoFecha;
    }

    public void setNacimientoFecha(Integer nacimientoFecha) {
        this.nacimientoFecha = nacimientoFecha;
    }

    public Integer getDecesoFecha() {
        return decesoFecha;
    }

    public void setDecesoFecha(Integer decesoFecha) {
        this.decesoFecha = decesoFecha;
    }

    public List<Libro> getLibroslibros() {
        return libros;
    }

    public void setLibroslibros(List<Libro> libroslibros) {
        this.libros = libroslibros;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "nombre='" + nombre + '\'' +
                ", nacimientoFecha=" + nacimientoFecha +
                ", decesoFecha=" + decesoFecha +
                ", libroslibros=" + libros +
                '}';
    }
}
