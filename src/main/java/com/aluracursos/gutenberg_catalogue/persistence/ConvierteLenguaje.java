package com.aluracursos.gutenberg_catalogue.persistence;

import com.aluracursos.gutenberg_catalogue.model.Lenguaje;
import jakarta.persistence.AttributeConverter;

import jakarta.persistence.Converter;


@Converter(autoApply = true)
public class ConvierteLenguaje implements AttributeConverter <Lenguaje,String> {

    @Override
    public String convertToDatabaseColumn(Lenguaje lenguaje) {
        return (lenguaje == null) ? null : lenguaje.getCategoriaGutendex();
    }

    @Override
    public Lenguaje convertToEntityAttribute(String dbData) {
        if (dbData ==null) return null;
        return Lenguaje.fromString(dbData);
    }
}
