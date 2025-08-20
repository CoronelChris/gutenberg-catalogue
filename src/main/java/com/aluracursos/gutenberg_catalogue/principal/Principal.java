package com.aluracursos.gutenberg_catalogue.principal;


import com.aluracursos.gutenberg_catalogue.model.Autor;
import com.aluracursos.gutenberg_catalogue.model.DatosAutor;
import com.aluracursos.gutenberg_catalogue.model.DatosLibro;
import com.aluracursos.gutenberg_catalogue.model.Libro;
import com.aluracursos.gutenberg_catalogue.repository.AutorRepository;
import com.aluracursos.gutenberg_catalogue.repository.LibroRepository;
import com.aluracursos.gutenberg_catalogue.service.ConsumoAPI;
import com.aluracursos.gutenberg_catalogue.service.ConvierteDatos;
import com.aluracursos.gutenberg_catalogue.service.Datos;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {

    private final AutorRepository autorRepositorio;
    private final LibroRepository libroRepositorio;

   private Scanner scanner = new Scanner(System.in);
   private ConsumoAPI consumoApi = new ConsumoAPI();
   private final String URL_BASE = "https://gutendex.com/books/";
   private final  String SEARCH = "?search=";
   private ConvierteDatos conversor = new ConvierteDatos();


    public Principal(AutorRepository autorRepository,  LibroRepository libroRepository) {
        this.autorRepositorio = autorRepository;
        this.libroRepositorio = libroRepository;
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

                case 2:
                    mostarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivosPorAnio();
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
        try {

            System.out.println("Escribe el nombre del Libro que deseas buscar: ");
            var buscarLibro = scanner.nextLine();
            String buscarLibroCodificado= URLEncoder.encode(buscarLibro, StandardCharsets.UTF_8);
            var json = consumoApi.obtenerDatos(URL_BASE + SEARCH +buscarLibroCodificado );
//            buscarLibro.toLowerCase().replace(" ","+")
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
        }catch (Exception e){
            throw new RuntimeException("Error al codificar el nombre de la serie ",e);
        }
//        Crear metodo muestralibro por titulo, poder imprimir el menu en el main y crear el repositorioAutor
    }
    private void muestraLibroPorTitulo() {
        DatosLibro datos = getDatosLibro();
        if (datos.autor().isEmpty()) {
            System.out.println("El libro no tiene autores registrados");
            System.out.println("Libro : "+ datos.titulo()+" "+ "Autor : "+ datos.autor() + " "+ "Idioma : "+ datos.idioma()
            + " "+ "Numero de descragas : " + datos.numeroDescargas());
            return;
        }
        DatosAutor datosAutor = datos.autor().get(0);

        Autor autor = autorRepositorio.findByNombreAndNacimientoFecha(datosAutor.nombre(),datosAutor.nacimientoFecha())
                .orElseGet(() -> autorRepositorio.save(
                        new Autor(
                                datosAutor.nombre(),
                                datosAutor.nacimientoFecha(),
                                datosAutor.decesoFecha()
                        )
                ));

        Libro libro = new Libro(datos, autor);
        autor.getLibros().add(libro);
        libroRepositorio.save(libro);

        System.out.println("Libro guardado: " + libro);
        }

        private void mostarLibrosRegistrados(){

        List<Libro>libros = libroRepositorio.findAll();

        libros.stream()
                .sorted(Comparator.comparing(Libro::getIdioma))
                .forEach(System.out::println);
        }


    private void mostrarAutoresRegistrados() {
       List <Autor>autor = autorRepositorio.findAll();

       autor.stream()
               .sorted(Comparator.comparing(Autor::getId))
               .forEach(System.out::println);

    }


    private void mostrarAutoresVivosPorAnio() {

        System.out.println("Escribe el año del que deseas ver que autores seguian vivos :");
        var anio = scanner.nextInt();
        scanner.nextLine();
        List<Autor> fechaAutor = autorRepositorio.findAutoresVivosEnAnio(anio);

        fechaAutor.stream()
                        .sorted(Comparator.comparing(Autor::getNacimientoFecha).reversed())
                .forEach(a -> System.out.println("Nombre : " + a.getNombre() + " Fecha de nacimiento : " + a.getNacimientoFecha()
                + " Fecha de fallecimiento : " + a.getDecesoFecha() + " Libros : " + a.getLibros()));
    }

    }





