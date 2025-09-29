package com.aluracursos.gutenberg_catalogue.model;

public enum EstadoDeLectura {
    PENDIENTE ("pendiente"),
    LEYENDO ("leyendo"),
    TERMINADO ("terminado");

    private String estadoDeLecturaActual;

    public String getEstadoDeLecturaActual() {
        return estadoDeLecturaActual;
    }

    EstadoDeLectura(String estadoDeLecturaActual){
        this.estadoDeLecturaActual=estadoDeLecturaActual;
    }

    public static EstadoDeLectura fromString (String text){
        if (text == null) return PENDIENTE;
        for (EstadoDeLectura estado : EstadoDeLectura.values()) {
            if (estado.estadoDeLecturaActual.equalsIgnoreCase(text)) {
                return estado;
            }
        }
        System.out.println("Estado invalido se: " + text + ". Se usara PENDIENTE por defecto");
        return PENDIENTE;
    }

    @Override
    public String toString() {
        return "EstadoDeLectura: " + estadoDeLecturaActual + '\'';
    }

}
