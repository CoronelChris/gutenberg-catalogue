package com.aluracursos.gutenberg_catalogue.principal;


import com.aluracursos.gutenberg_catalogue.model.*;
import com.aluracursos.gutenberg_catalogue.repository.AutorRepository;
import com.aluracursos.gutenberg_catalogue.repository.LibroRepository;
import com.aluracursos.gutenberg_catalogue.service.ConsumoAPI;
import com.aluracursos.gutenberg_catalogue.service.ConvierteDatos;
import com.aluracursos.gutenberg_catalogue.service.Datos;
import org.springframework.stereotype.Component;

import java.io.Serializable;
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
                    6- Ver estado del libro (Pendiente, Leyendo o Leído)
                    
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

                case 5:
                    mostrarLibrosPorIdioma();
                    break;

                case 6:
                    mostrarEstadoDelLibro();
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
            throw new RuntimeException("Error al codificar el nombre del libro ",e);
        }
//        Crear metodo muestralibro por titulo, poder imprimir el menu en el main y crear el repositorioAutor
    }

    private void muestraLibroPorTitulo() {
        // 1️ Obtenemos los datos del libro desde la API
        DatosLibro datos = getDatosLibro();
        if (datos == null){
            System.out.println("No se pudo obtener informacion del libro");
            return;
        }
        // 2️ Verificamos si el libro tiene autor
        if (datos.autor().isEmpty()) {
            System.out.println("El libro no tiene autores registrados");
            System.out.println("Libro : "+ datos.titulo()+" "+ "Autor : "+ datos.autor() + " "+ "Idioma : "+ datos.idioma()
            + " "+ "Numero de descragas : " + datos.numeroDescargas());
            return;
        }
        // 3️ Obtenemos el primer autor del resultado
        DatosAutor datosAutor = datos.autor().get(0);

        // 4  Buscamos si ese autor ya está en la BD
        Autor autor = autorRepositorio.findByNombreAndNacimientoFecha(datosAutor.nombre(),datosAutor.nacimientoFecha())
                .orElseGet(() -> autorRepositorio.save(
                        new Autor(
                                datosAutor.nombre(),
                                datosAutor.nacimientoFecha(),
                                datosAutor.decesoFecha()
                        )
                ));
        // 5️✅ Verificamos si el libro YA existe (por título y nombre del autor)

        Optional<Libro>libroExistente = libroRepositorio.findByTituloAndAutorNombre(
                datos.titulo(),
                datosAutor.nombre()
        );

        if (libroExistente.isPresent()){
            // Si ya existe, mostramos mensaje y salimos
            System.out.println("⚠️ El libro \""+datos.titulo()+"\" ya existe en tu biblioteca.");
            return;
        }
        // 6️ Si no existe, lo creamos y lo guardamos
        Libro libro = new Libro(datos, autor);
        autor.getLibros().add(libro);
        libroRepositorio.save(libro);

        // 7️Mostramos confirmación
        System.out.println("Libro guardado: " + libro);
        System.out.println(libro);
        }
        private void mostarLibrosRegistrados(){

        List<Libro>libros = libroRepositorio.findAll();

        libros.stream()
                .sorted(Comparator.comparing(Libro::getId))
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

    private void mostrarLibrosPorIdioma() {
        System.out.println("Escribe el idioma de los libros que deseas consultar");
        var idiomaLibros = scanner.nextLine();

        Lenguaje lenguaje = Lenguaje.fromUserInput(idiomaLibros);

        List<Libro> librosPorIdioma =libroRepositorio.findByIdioma(lenguaje);

        librosPorIdioma.stream()
                .sorted(Comparator.comparing(Libro::getIdioma))
                .forEach(System.out::println);
    }


    private void mostrarEstadoDelLibro() {
       mostarLibrosRegistrados();
        System.out.println("Esta es nuestra biblioteca, ecoge por ID el libro que deseas ver");
        var libroPorId = scanner.nextLong();
        scanner.nextLine();

        Optional<Libro> idLibro = libroRepositorio.findById(libroPorId);

        if (idLibro.isPresent()){
            Libro libroEncontrado =idLibro.get();
            libroEncontrado.getEstado();
            System.out.println(libroEncontrado);

            System.out.println( """
                    1-  Cambiar estado a LEYENDO .
                    2-  Cambiar estado a LEIDO.                
                    0 - Mantener estado
                    """);

                   int  opcion = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcion) {

                        case 1 -> {
                            libroEncontrado.setEstado(EstadoDeLectura.LEYENDO);
                            System.out.println("Actualizando estado a LEYENDO");
                        }
                        case 2 ->{
                            libroEncontrado.setEstado(EstadoDeLectura.TERMINADO);
                        System.out.println("Actualizando estado a LEIDO");
                        }

                        case 0 -> System.out.println("Saliendo sin cambios...");
                        default -> System.out.println("Opcion invalida");
                    }
            libroRepositorio.save(libroEncontrado);
        }


    }
}





