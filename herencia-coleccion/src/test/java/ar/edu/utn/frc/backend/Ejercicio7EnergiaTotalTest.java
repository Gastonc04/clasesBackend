package ar.edu.utn.frc.backend;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Ejercicio 7 (ejercicio 12 del enunciado) - Energia total diaria requerida por todo el establecimiento.
 */
class Ejercicio7EnergiaTotalTest {

	private static double calcularEnergiaTotal(final List<Animal> animales) {

		double total = 0;

		for (final Animal animal : animales) {
			total += animal.calcularEnergiaTotalDiaria();
		}

		return total;
	}

	@Test
	void elEstablecimientoNecesitaSeisMilQuinientasTreceMcal() {
		assertEquals(6513.18, calcularEnergiaTotal(DatosDePrueba.animales()), 0.01);
	}

	@Test
	void unAnimalSinDietaNoSumaEnergia() {
		assertEquals(0.0, calcularEnergiaTotal(List.of(DatosDePrueba.bovinoHereford())), 0.01);
	}

	@Test
	void elCasoTestigoAportaMilDoscientosOchentaYSieteMcal() {
		assertEquals(1287.0, calcularEnergiaTotal(List.of(DatosDePrueba.casoTestigo())), 0.01);
	}
}
