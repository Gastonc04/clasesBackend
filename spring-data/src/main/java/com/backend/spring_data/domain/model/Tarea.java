package com.backend.spring_data.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static io.micrometer.common.util.StringUtils.isBlank;

public class Tarea {
    private long id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaVencimiento;
    private LocalDateTime fechaCreacion;

    public Tarea(long id, String nombre, String descripcion, LocalDate fechaVencimiento, LocalDateTime fechaCreacion) {


        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaCreacion = fechaCreacion;
    }

    private void setTitulo(String titulo) {
        if(isBlank(titulo) || titulo.length() > 100) {
            throw IllegalArgumentException("");
        }
    }



}
