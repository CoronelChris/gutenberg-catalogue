package com.aluracursos.gutenberg_catalogue.model;

public enum Lenguaje {
    INGLÉS("en"),
    ESPAÑOL("es"),
    PORTUGUÉS("pt"),
    ALEMÁN("de"),
    FRANCÉS("fr");

    private String categoriaGutendex;


    public String getCategoriaGutendex() {
        return categoriaGutendex;
    }

    public void setCategoriaGutendex(String categoriaGutendex) {
        this.categoriaGutendex = categoriaGutendex;
    }

    Lenguaje(String categoriaGutendex) {
        this.categoriaGutendex = categoriaGutendex;

    }

    public static Lenguaje fromString(String text) {
        if (text == null) return null;
        String limpio = text.replace("[", "").replace("]", "").trim();
        for (Lenguaje categoria : Lenguaje.values()) {
            if (categoria.categoriaGutendex.equalsIgnoreCase(limpio)) {
                return categoria;
            }

        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }
}
