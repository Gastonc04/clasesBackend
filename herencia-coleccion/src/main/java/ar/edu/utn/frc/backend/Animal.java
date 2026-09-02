package ar.edu.utn.frc.backend;

import java.time.LocalDate;

public abstract class Animal implements Alimentable, Comparable<Animal> {
    protected float peso;
    private final LocalDate fechaNacimiento;
    private final Sexo sexo;
    private final Raza raza;
    private Dieta dieta;

    public Animal(float aPeso, LocalDate fechaNacimiento, Sexo aSexo, Raza aRaza)

    public float getPeso()
}
