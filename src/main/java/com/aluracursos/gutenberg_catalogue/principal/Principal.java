package com.aluracursos.gutenberg_catalogue.principal;


import com.aluracursos.gutenberg_catalogue.model.DatosLibro;
import com.aluracursos.gutenberg_catalogue.model.Libro;
import com.aluracursos.gutenberg_catalogue.repository.AutorRepository;
import com.aluracursos.gutenberg_catalogue.service.ConsumoAPI;
import com.aluracursos.gutenberg_catalogue.service.ConvierteDatos;
import com.aluracursos.gutenberg_catalogue.service.Datos;

import java.util.Optional;
import java.util.Scanner;

public class Principal {

   private Scanner scanner = new Scanner(System.in);
   private ConsumoAPI consumoApi = new ConsumoAPI();
   private final String URL_BASE = "https://gutendex.com/books/";
   private final  String SEARCH = "?search=";
   private ConvierteDatos conversor = new ConvierteDatos();
   private AutorRepository repositoio;

    public Principal(AutorRepository repository) {
        this.repositoio=repository;
    }


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1- Buscar libro por titulo.
                    2- Ver libros registrados.
                    3- Ver autores registrados.
                    4- Ver autores vivos en determinado año en especifico.
                    5- Ver libros por idioma.
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:
                    muestraLibroPorTitulo();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private  DatosLibro getDatosLibro(){
        System.out.println("Escribe el nombre del Libro que deseas buscar: ");
        var buscarLibro = scanner.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + SEARCH + buscarLibro.toLowerCase().replace(" ","+"));
        System.out.println(json);
        Datos datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibro> buscandoLibro= datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toLowerCase().contains(buscarLibro.toLowerCase()))
                .findFirst();
        if (buscandoLibro.isPresent()){
            System.out.println("Libro encontrado");
          return buscandoLibro.get();
        }else{
            System.out.println("Libro no encontrado");
            return null;
        }
//        Crear metodo muestralibro por titulo, poder imprimir el menu en el main y crear el repositorioAutor
    }
    private void muestraLibroPorTitulo() {
        DatosLibro datos = getDatosLibro();
        if (datos != null) {
            System.out.println(datos);
        }

    }
}



