package ar.edu.utn.frc.backend;

import java.util.List;


public class Dieta {

	private static final double TOTAL_ESPERADO = 100.0;
	private static final double EPSILON = 1e-6;

	private final String nombre;
	private final String descripcion;
	private final List<Racion> raciones;

	public Dieta(final String aNombre, final String aDescripcion, final List<Racion> aRaciones) {

		if (aRaciones == null || aRaciones.isEmpty()) {
			throw new IllegalArgumentException("La dieta '" + aNombre + "' debe tener al menos una racion.");
		}

		double total = 0;

		for (final Racion racion : aRaciones) {
			total += racion.getPorcentaje();
		}

		if (Math.abs(total - TOTAL_ESPERADO) > EPSILON) {
			throw new IllegalArgumentException(
				"Los porcentajes de la dieta '" + aNombre + "' deben sumar 100 y suman " + total + ".");
		}

		nombre = aNombre;
		descripcion = aDescripcion;
		raciones = List.copyOf(aRaciones);
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public List<Racion> getRaciones() {
		return raciones;
	}

	/**
	 * Energia que aporta, en promedio, cada kg de esta dieta (Mcal/kg).
	 * Se calcula sobre los porcentajes, sin necesidad de conocer los kg.
	 */
	public double calcularDensidadEnergetica() {

		double densidad = 0;

		for (final Racion racion : raciones) {
			densidad += racion.aporteEnergetico();
		}

		return densidad;
	}

	/**
	 * Energia total (Mcal) aportada por una cantidad dada de esta dieta.
	 */
	public double calcularEnergiaTotal(final double kgTotales) {
		return kgTotales * calcularDensidadEnergetica();
	}

	@Override
	public String toString() {

		final StringBuilder sb = new StringBuilder();

		sb.append(nombre).append(" - ").append(descripcion).append("\n");

		for (final Racion racion : raciones) {
			sb.append("  ").append(racion).append("\n");
		}

		sb.append(String.format("  Densidad energetica: %.2f Mcal/kg", calcularDensidadEnergetica()));

		return sb.toString();
	}
}
