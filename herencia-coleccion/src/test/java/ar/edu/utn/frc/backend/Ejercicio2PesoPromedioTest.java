package ar.edu.utn.frc.backend;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Ejercicio 2 (ejercicio 3 del enunciado) - Peso promedio de una List<Animal>.
 *
 * <p>Animal.peso es float, pero el acumulador y el resultado son double para no
 * perder precision.
 */
class Ejercicio2PesoPromedioTest {

	private static double sumarPesos(final List<Animal> animales) {

		double total = 0;

		for (final Animal animal : animales) {
			total += animal.getPeso();
		}

		return total;
	}

	private static double calcularPesoPromedio(final List<Animal> animales) {

		if (animales.isEmpty()) {
			return 0;
		}

		return sumarPesos(animales) / animales.size();
	}

	@Test
	void elPromedioDeLosSieteAnimalesEsCientoNoventaYDosComaOchentaYSeis() {
		assertEquals(192.86, calcularPesoPromedio(DatosDePrueba.animales()), 0.01);
	}

	@Test
	void losSieteAnimalesPesanMilTrescientosCincuentaKilos() {
		assertEquals(1350.0, sumarPesos(DatosDePrueba.animales()), 0.01);
	}

	@Test
	void elPromedioDeUnaListaVaciaEsCero() {
		assertEquals(0.0, calcularPesoPromedio(List.of()), 0.01);
	}
}
