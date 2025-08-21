package com.aluracursos.gutenberg_catalogue.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String nombre;
    private Integer nacimientoFecha;
    private Integer decesoFecha;
    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro>libros = new ArrayList<>();

    public Autor() {}


    public Autor(String nombre, Integer nacimientoFecha, Integer decesoFecha) {
        this.nombre = nombre;
        this.nacimientoFecha = nacimientoFecha;
        this.decesoFecha = decesoFecha;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {

        libros.forEach(l ->l.setAutor(this));
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

    @Override
    public String toString() {
        return
                "nombre='" + nombre + '\'' +
                ", nacimientoFecha=" + nacimientoFecha +'\'' +
                ", decesoFecha=" + decesoFecha +'\'' +
                ", libros=" + libros;
    }
}
