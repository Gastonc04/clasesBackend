package ar.edu.utn.frc.backend;

import java.util.Objects;

public final class Racion {

	private final Alimento alimento;
	private final double porcentaje;

	public Racion(final Alimento aAlimento, final double aPorcentaje) {

		if (aAlimento == null) {
			throw new IllegalArgumentException("La racion debe indicar un alimento.");
		}

		if (aPorcentaje <= 0 || aPorcentaje > 100) {
			throw new IllegalArgumentException(
				"El porcentaje de " + aAlimento + " debe estar entre 0 y 100, y se recibio " + aPorcentaje + ".");
		}

		alimento = aAlimento;
		porcentaje = aPorcentaje;
	}

	public Alimento getAlimento() {
		return alimento;
	}

	public double getPorcentaje() {
		return porcentaje;
	}

	/**
	 * Aporte de esta racion a la densidad energetica de la dieta, en Mcal por kg de dieta.
	 */
	public double aporteEnergetico() {
		return (porcentaje / 100.0) * alimento.getEnergiaMcalKg();
	}

	/**
	 * Kilogramos de este alimento que corresponden a un consumo total dado.
	 */
	public double calcularKilogramos(final double kgTotales) {
		return kgTotales * (porcentaje / 100.0);
	}

	@Override
	public boolean equals(final Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		return alimento == ((Racion) obj).alimento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(alimento);
	}

	@Override
	public String toString() {
		return "[ " + porcentaje + "% de " + alimento + " ]";
	}
}
