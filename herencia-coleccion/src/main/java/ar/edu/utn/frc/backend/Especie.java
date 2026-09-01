package ar.edu.utn.frc.backend;

public enum Especie {
	BOVINO("Bovino"),
	PORCINO("Porcino"),
	OVINO("Ovino");

	private final String nombre;

	Especie(final String aNombre) {
		nombre = aNombre;
	}

	public String getNombre() {
		return nombre;
	}
}
