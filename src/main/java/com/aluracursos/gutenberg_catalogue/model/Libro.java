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
//    @Enumerated(EnumType.STRING)
    private Lenguaje idioma;
    private Double numeroDescargas;
    @ManyToOne
    private Autor autor;



    public Libro() {
    }

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idioma = Lenguaje.fromString(datosLibro.idioma().get(0));
        this.numeroDescargas = datosLibro.numeroDescargas();
    }

    public Libro(String titulo, String idioma, Double numeroDescargas, Autor autor) {
        this.titulo = titulo;
        this.idioma = Lenguaje.fromString(idioma);
        this.numeroDescargas = numeroDescargas;
        this.autor = autor;
    }

    public Libro(DatosLibro datos, Autor autor) {
        this.titulo = datos.titulo();
        this.idioma = Lenguaje.fromString(datos.idioma().get(0));
        this.numeroDescargas = datos.numeroDescargas();
        this.autor = autor;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Lenguaje getIdioma() {
        return idioma;
    }

    public void setIdioma(Lenguaje idioma) {
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
                ", idioma='" + (idioma != null ? idioma.name(): "No asignado" )+ '\'' +
                ", autor=" + (autor != null ?autor.getNombre() : "No asignado")+'\'' +
                ", numeroDescargas=" + numeroDescargas;
    }
}
