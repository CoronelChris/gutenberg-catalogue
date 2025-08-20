package com.aluracursos.gutenberg_catalogue;

import com.aluracursos.gutenberg_catalogue.principal.Principal;
import com.aluracursos.gutenberg_catalogue.repository.AutorRepository;
import com.aluracursos.gutenberg_catalogue.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GutenbergCatalogueApplication implements CommandLineRunner {

	private final Principal principal;

	public GutenbergCatalogueApplication(Principal principal) {
		this.principal = principal;
	}

	public static void main(String[] args) {
		SpringApplication.run(GutenbergCatalogueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		principal.muestraElMenu();



	}
}
