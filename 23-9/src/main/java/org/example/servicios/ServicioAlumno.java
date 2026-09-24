package org.example.servicios;

import java.util.List;

public class ServicioAlumno {
    private RepositorioAlumnos repo;

    public ServicioAlumno() {

        System.out.println("Constructor ServicioAlumnos");
    }

    public List<Alumno> getAlumno() {
        Alumno[] alumno = repo.readAlumno();
        return List.of(alumno);
    }
}
