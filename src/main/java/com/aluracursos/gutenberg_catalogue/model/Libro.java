package com.aluracursos.gutenberg_catalogue.model;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String idioma;
    private Double numeroDescargas;
    @ManyToOne
    private Autor autor;



    public Libro() {
    }

    public Libro(String titulo, String idioma, Double numeroDescargas, Autor autor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.numeroDescargas = numeroDescargas;
        this.autor = autor;
    }

    public Libro(DatosLibro datos, Autor autor) {
        this.titulo = datos.titulo();
        this.idioma = String.valueOf(datos.idioma());
        this.numeroDescargas = datos.numeroDescargas();
        this.autor = autor;

    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "Libro:" +
                "titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", autor=" + (autor != null ?autor.getNombre() : "No asignado")+'\'' +
                ", numeroDescargas=" + numeroDescargas;
    }
}
